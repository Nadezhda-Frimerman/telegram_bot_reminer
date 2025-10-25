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
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

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
    void process_startMessage_sendsHello() {
        // Моки входных объектов
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(123L);

        // Мок SendResponse - нельзя создавать напрямую
        SendResponse mockResponse = mock(SendResponse.class);
        when(mockResponse.isOk()).thenReturn(true);
        when(telegramBot.execute(any(SendMessage.class))).thenReturn(mockResponse);

        int result = telegramBotUpdatesListener.process(Collections.singletonList(update));


        assertEquals(UpdatesListener.CONFIRMED_UPDATES_ALL, result);
    }


}