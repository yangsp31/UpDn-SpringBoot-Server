package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Domain.RealTimeDTO;
import Project.UpDn_SP_Server.Domain.SummaryDTO;
import Project.UpDn_SP_Server.Repository.Mapping.SummaryMapping;
import Project.UpDn_SP_Server.Repository.UpDnRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

// 스프링 부트 서버로 특정 엔드포인트를 사용하여 요청시 각각의 엔드포인트에 따라 실행 할 작업을 선언한 클래스
public class DataProcessing {
    private final UpDnRepository updnRepository;
    private final UseGPT useGPT;
    private final TextProcessing textProcessing;
    private final RequestFlask requestFlask;

    @Autowired
    private ObjectMapper objectMapper;

    // Bean에 등록되어있는 객체를 받아와서 사용 (injection)
    public DataProcessing(UpDnRepository updnRepository, UseGPT useGPT, TextProcessing textProcessing, RequestFlask requestFlask) {
        this.updnRepository = updnRepository;
        this.useGPT = useGPT;
        this.textProcessing = textProcessing;
        this.requestFlask = requestFlask;
    }

    // /UpDn/SP/Request/Gpt 엔드포인트로 들어온 요청을 처리하는 로직/DB에서 keyword에 해당하는 데이터를 읽어온 후 해당 데이터를 gpt-4 모델에게 전달 후 작업 수행
    public SummaryDTO SummaryDataProcess (String keyword) {
        String AllSummary;
        LocalDate NowDate = LocalDate.now();  // 현재날짜 받아옴
        String Result;
        long Count = 0;
        Pageable pageable = PageRequest.of(0, 13, Sort.by("dataIndex").descending());

        // JPA를 사용하여 Mysql 쿼리 실행/반환값은 SummaryMapping을 통해 articlesummary 컬럼의 데이터만을 List에 저장
        List<SummaryMapping> Summary = updnRepository.findByPublicationDateAndArticleSummaryLike(NowDate, "%" + keyword + "%", pageable);

        // 쿼리 실행 후 반환받은 값이 없을경우(NULL) 처리 로직
        if (Summary.isEmpty()) {

            // 현재날짜를 기준으로 현재 날짜를 제외한 7일간의 데이터 탐색
            Summary = updnRepository.findByPublicationDateBetweenAndArticleSummaryLike
                    (NowDate.minusDays(1),NowDate.minusDays(6), "%" + keyword + "%", pageable);

            // 7일 전까지도 데이터가 없을경우 반환 로직
            if (Summary.isEmpty()) {
                return textProcessing.NoneData();
            }
        }
        // 데이터가 1개 이상으로 존재할 경우 각 데이터(뉴스 기사 요약본<String>)를 ',' 를 사용하여 구분하여 하나의 String으로 만든 후 GPT-4 모델에게 분석 데이터로서 전달
        AllSummary = Summary.stream().map(SummaryMapping::getArticleSummary).collect(Collectors.joining(", "));
        System.out.println(AllSummary);
        Result = useGPT.GptResponse(AllSummary);

        // GPT-4 모델에서 반환받은 데이터 반환 로직
        return textProcessing.TextProcess(Result);
    }

    // /UpDn/SP/Request/Flask 엔드포인트로 들어온 요청을 처리하는 로직/Flask 서버에 실시간으로 USD, JYP 현재가/현재 최다 검색 기업명 데이터를 요청하고 반환하는 로직
    public RealTimeDTO RealtimeDataProcess() {
        try {
            // Flask 서버에 데이터 요청
            String FlaskData = requestFlask.getRealTimeData();

            // 전달받은 데이터를 RealTimeDTO 형태에 맞게 매핑
            RealTimeDTO realTimeDTO = objectMapper.readValue(FlaskData, RealTimeDTO.class);

            return realTimeDTO;
        } catch (Exception e) {
            System.out.println(e);

            // 오류 발생시 데이터가 존재하지 않는(NULL) RealTimeDTO 객체전달
            return new RealTimeDTO();
        }
    }
}
