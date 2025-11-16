package top.yanquithor.framework.dddbase.ai.infrastructure.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.yanquithor.framework.dddbase.ai.domain.ChatRepository;
import top.yanquithor.framework.dddbase.ai.infrastructure.memroy.QuithorChatMemeryImpl;

@Configuration
public class ChatConfig {
    
    @Bean
    public ChatMemory chatMemory(ChatRepository repository) {
        return new QuithorChatMemeryImpl();
    }
}
