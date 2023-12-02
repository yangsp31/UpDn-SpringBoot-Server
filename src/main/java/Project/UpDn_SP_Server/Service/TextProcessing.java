package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Domain.SummaryDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 요청받은 데이터를 전달하기 위한 데이터 형태 구성 클래스
public class TextProcessing {

    public SummaryDTO TextProcess(String result) {
        // GPT-4 의 결과값을 '/'를 기준으로 쪼개기 {GPT-4 결과값 ex) "A"/"B"/"C"/"D"}
        String[] elements = result.split("/");

        SummaryDTO summaryDTO = new SummaryDTO();

        summaryDTO.setResult(elements[0]);
        // summaryDTO의 reason에 저장할 List 생성/elements[1] 부터 elements[elements.length - 1] 까지의 데이터 입력
        List<String> reasons = new ArrayList<>(Arrays.asList(elements).subList(1, elements.length));

        summaryDTO.setReason(reasons);

        return summaryDTO;
    }

    // GPT-4에 분석을 맡길 데이터가 없을경우 반환할 값 처리 로직
    public SummaryDTO NoneData() {
        // 데이터가 없는 SummaryDTO 객체 생성 후 반환
        return new SummaryDTO();
    }
}
