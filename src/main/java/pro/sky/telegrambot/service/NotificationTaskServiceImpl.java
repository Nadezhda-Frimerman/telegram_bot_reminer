package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotificationTaskServiceImpl implements NotificationTaskService {
    private NotificationTaskRepository notificationTaskRepository;
    private final String format= "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";

    public NotificationTaskServiceImpl(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public boolean checkPattern(String input) {
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public NotificationTask createNotificationTask(Long chatId, String input) {
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Входная строка не соответствует формату");
        }
        String time = matcher.group(1);
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        String text = matcher.group(3);
        return new NotificationTask(chatId, text, dateTime);
    }
    public List<NotificationTask> getTasks() {
        LocalDateTime dateTimeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        return notificationTaskRepository.findAllNotificationTaskByTime(dateTimeNow);
    }
}