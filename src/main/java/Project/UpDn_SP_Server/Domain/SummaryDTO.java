package Project.UpDn_SP_Server.Domain;

import lombok.Getter;

import java.util.List;

// 스프링부트 서버로 gpt-4 데이터 분석 및 예측 결과 데이터를 요청시 반환하는 DTO(Data Transfer Object)
@Getter
public class SummaryDTO {
    private String Result;
    private List<String> Reason;

    public void setResult(String result) {
        this.Result = result;
    }

    public void setReason(List<String> reason) {
        this.Reason = reason;
    }
}
