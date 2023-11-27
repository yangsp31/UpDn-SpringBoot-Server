package Project.UpDn_SP_Server.Service;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

public class UseGPT {
    private final ChatgptService chatgptService;

    @Value("${Gpt.role}")
    private String role;

    public UseGPT(ChatgptService chatgptService) {
        this.chatgptService = chatgptService;
    }

    public String GptResponse(String Data) {
        List<MultiChatMessage> messages = Arrays.asList(
                new MultiChatMessage("system", role),
                new MultiChatMessage("user", Data));

        return chatgptService.multiChat(messages);
    }
}
