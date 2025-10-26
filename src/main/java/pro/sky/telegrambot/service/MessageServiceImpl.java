package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final NotificationTaskRepository notificationTaskRepository;
    private final NotificationTaskServiceImpl notificationTaskService;

    public MessageServiceImpl (NotificationTaskRepository notificationTaskRepository, NotificationTaskServiceImpl notificationTaskService, TelegramBot telegramBot) {
        this.notificationTaskRepository = notificationTaskRepository;
        this.notificationTaskService = notificationTaskService;
    }
    public SendMessage createMessage (String text,Long chatId) {
        SendMessage sendMessage;

        if (Objects.equals("/start",text)) {
            sendMessage = new SendMessage(chatId,
                    "Привет! Напиши напоминание в формате 01.01.2022 20:00 текст и я напомню в указанное время.");
        }
        else if (notificationTaskService.checkPattern(text)) {
            NotificationTask notificationTask = notificationTaskService.createNotificationTask(chatId, text);
            if (!notificationTask.getDateTime().isBefore(LocalDateTime.now())) {
                sendMessage = new SendMessage(chatId, "Принято!");
                notificationTaskRepository.save(notificationTask);
            } else {
                sendMessage = new SendMessage(chatId, "Время прошло. Напиши корректное время и дату.");
            }
        } else {
            sendMessage = new SendMessage(chatId, "Напиши напоминание в формате 01.01.2022 20:00 текст.");
        };
        return sendMessage;
    }
    public List<SendMessage> getReminders (){
       List<NotificationTask> tasks = notificationTaskService.getTasks();
        return tasks.stream()
                .map(task -> new SendMessage(task.getChatId(), task.getText()))
                .collect(Collectors.toList());

    }
}
