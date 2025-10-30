package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;

import java.util.List;

@Service
public interface NotificationTaskService {
    boolean checkPattern(String input);
    NotificationTask createNotificationTask(Long chatId, String input);
    List<NotificationTask> getTasks();
}
