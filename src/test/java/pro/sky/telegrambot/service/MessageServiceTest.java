package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private NotificationTaskRepository notificationTaskRepository;
@Mock
private NotificationTaskServiceImpl notificationTaskService;
    @InjectMocks
    private MessageServiceImpl messageServiceImpl;

    @Test
    void createMessage() {
        String text = "/start";
        Long chatId = 123L;
     //test//
    SendMessage sendMessage = messageServiceImpl.createMessage(text,chatId);
    //check//
        assertThat(sendMessage.getParameters().get("chat_id")).isEqualTo(chatId);
        assertThat(sendMessage.getParameters().get("text")).isEqualTo("Привет! Напиши напоминание в формате 01.01.2022 20:00 текст и я напомню в указанное время.");
    }

    @Test
    void getReminders() {
        NotificationTask task = new NotificationTask(123L,"Ok", LocalDateTime.now());
        Mockito.when(notificationTaskService.getTasks()).thenReturn(List.of(task));
        //test//
        List<SendMessage> reminders = messageServiceImpl.getReminders();
        //check//
        assertThat(reminders).isNotEmpty().hasSize(1).first().satisfies(s->{
            assertThat(s.getParameters().get("chat_id")).isEqualTo(task.getChatId());
            assertThat(s.getParameters().get("text")).isEqualTo(task.getText());
        });
    }
}