package Project.UpDn_SP_Server.Service;

import org.springframework.web.client.RestTemplate;

// Flask 서버에 데이터 요청
public class RequestFlask {
    private final RestTemplate restTemplate;
    //Flask 서버의 API 주소
    private final String url = "http://localhost:5000/Flask/realtimeData";

    // Bean에 등록되어있는 객체를 받아와서 사용 (injection)
    public RequestFlask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Flask 서버에 해당 API 주소와 반환받을 형태를 명시하여 요청
    public String getRealTimeData() {
        return restTemplate.getForObject(url, String.class);
    }
}
