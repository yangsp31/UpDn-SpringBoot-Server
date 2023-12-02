package Project.UpDn_SP_Server.Controller;

import Project.UpDn_SP_Server.Domain.RealTimeDTO;
import Project.UpDn_SP_Server.Domain.SummaryDTO;
import Project.UpDn_SP_Server.Service.DataProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 스프링부트 서버 요청 처리 로직
@RestController
public class UpDnController {
    private final DataProcessing dataProcessing;

    @Autowired
    public UpDnController(DataProcessing dataProcessing) {
        this.dataProcessing = dataProcessing;   //Bean에 등록된 DataProcessing 객체 받아와서 사용 (injection)
    }

    // gpt-4 를 사용하여 데이터를 분석하고 예측한 결과값을 반환받아 데이터를 요청한 어플리케이션으로 반환 해주는 엔드포인트/메소드
    @GetMapping("/UpDn/SP/Request/Gpt")
    public SummaryDTO gptExpectation(@RequestParam(name = "keyWord") String keyword) {
        return dataProcessing.SummaryDataProcess(keyword);
    }

    // USD, JYP 현재가/현재 최다 검색 기업명을 Flask 서버에 요청 후 반환받아 데이터를 요청한 어플리케이션으로 반환 해주는 엔드포인트/메소드
    @GetMapping("/UpDn/SP/Request/Flask")
    public RealTimeDTO RealtimeData() {
        return dataProcessing.RealtimeDataProcess();
    }
}
