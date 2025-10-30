package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.MessageService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelegramBotUpdatesListenerTest {

    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    private MessageService messageService;

    @Mock
    private NotificationTaskRepository notificationTaskRepository;

    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }
    @Test
    void process_startMessage_sendHello() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        SendResponse mockResponse = mock(SendResponse.class);
        when(mockResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(mockResponse);

        int result = telegramBotUpdatesListener.process(Collections.singletonList(update));

        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }
//    @Test
//    public void testProcessWithValidReminder() {
//        // Создаем Update с корректным текстом и chatId
//        Long chatId = 123L;
//        String text = "01.11.2025 20:00 Тест";
//
//        Update update = createUpdate(text, chatId);
//
//        // Мокаем telegramBot.execute, чтобы возвращал SendResponse
//        SendResponse mockResponse = mock(SendResponse.class);
//        when(telegramBot.execute(any())).thenReturn(mockResponse);
//
//        int result = telegramBotUpdatesListener.process(List.of(update));
//
//        // Проверяем, что ответ успешен
//        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
//
//        // Проверяем, что задача создалась и сохранена в базе
//        List<NotificationTask> tasks = notificationTaskRepository.findAll();
//        assertFalse(tasks.isEmpty());
//        assertEquals(chatId, tasks.get(0).getChatId());
//
//        // Можно проверить текст и дату задачи
//        assertEquals("Тест", tasks.get(0).getText());
//        assertTrue(tasks.get(0).getDateTime().isAfter(LocalDateTime.now()));
//    }
//    private Update createUpdate(String text, Long chatId) {
//        // Создайте и настройте объект Update, Message, Chat
//        // Можно использовать Mockito, если объекты сложно создавать вручную
//        Update update = mock(Update.class);
//        Message message = mock(Message.class);
//        Chat chat = mock(Chat.class);
//
//        when(update.message()).thenReturn(message);
//        when(message.text()).thenReturn(text);
//        when(message.chat()).thenReturn(chat);
//        when(chat.id()).thenReturn(chatId);
//
//        return update;
//    }
}