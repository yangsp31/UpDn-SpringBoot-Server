package Project.UpDn_SP_Server.Domain;

import lombok.Getter;

import java.util.List;

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
