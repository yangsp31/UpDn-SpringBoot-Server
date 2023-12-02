package Project.UpDn_SP_Server.Domain;

import lombok.Getter;

import java.util.List;

// 스프링부트 서버로 USD, JYP 현재가/현재 최다 검색 기업명 데이터를 요청시 반환하는 DTO(Data Transfer Object)
@Getter
public class RealTimeDTO {
    private List<String> NowRank;
    private List<String> rate;

    public void setNowRank(List<String> nowRank) {
        this.NowRank = nowRank;
    }

    public void setRate(List<String> rate) {
        this.rate = rate;
    }
}
