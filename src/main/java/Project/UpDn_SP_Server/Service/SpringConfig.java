package Project.UpDn_SP_Server.Service;

import Project.UpDn_SP_Server.Repository.UpDnRepository;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private UpDnRepository updnRepository;
    private ChatgptService chatgptService;

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
        return new DataProcessing(updnRepository, useGPT());
    }
}
