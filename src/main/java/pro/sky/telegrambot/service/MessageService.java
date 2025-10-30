package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    SendMessage createMessage(String text,Long chatId);
    List<SendMessage> getReminders ();
}
