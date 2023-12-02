package Project.UpDn_SP_Server.Service;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

// GPT-4 API 연결 및 사용 로직 클래스
public class UseGPT {
    private final ChatgptService chatgptService;

    @Value("${Gpt.role}")
    private String role;

    // 스프링부트 Bean에 등록된 객체 사용 (injection)
    public UseGPT(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    // GPT-4 API에 설정한 system role과 데이터 전달/결과값 받는 로직
    public String GptResponse(String Data) {
        List<MultiChatMessage> messages = Arrays.asList(
                new MultiChatMessage("system", role),      // GPT-4가 전달받은 데이터를 기반으로 사용자가 지정한 역할에 맞는 응답을 도출하게 유도하는 위한 문장
                new MultiChatMessage("user", Data));       // GPT-4에 전달할 데이터(String)

        return chatgptService.multiChat(messages);        // GPT-4에서 받은 결과밧 반환(String)
    }
}
