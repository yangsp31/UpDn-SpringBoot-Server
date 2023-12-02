package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Repository.UpDnRepository;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Spring Boot Bean을 관리하기위한 클래스
@Configuration
public class SpringConfig {

    private final UpDnRepository updnRepository;
    private final ChatgptService chatgptService;

    public SpringConfig(UpDnRepository updnRepository, ChatgptService chatgptService) {
        this.updnRepository = updnRepository;
        this.chatgptService = chatgptService;
    }

    @Bean
    public UseGPT useGPT() {
        return new UseGPT(chatgptService);
    }

    @Bean
    public DataProcessing dataProcessing() {
        return new DataProcessing(updnRepository, useGPT(), textProcessing(), requestFlask());
    }

    @Bean
    public  TextProcessing textProcessing() {
        return new TextProcessing();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RequestFlask requestFlask() {
        return new RequestFlask(restTemplate());
    }
}
