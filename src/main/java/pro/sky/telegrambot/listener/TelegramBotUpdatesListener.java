package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final NotificationTaskRepository notificationTaskRepository;

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String text = update.message().text();
            Long chatId = update.message().chat().id();

            if (text.equals("/start")) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Привет!"));
            }
            if (checkPattern(text)) {
                NotificationTask notificationTask = createNotificationTask(chatId, text);
                if (!notificationTask.getDateTime().isBefore(LocalDateTime.now())) {
                    SendResponse response = telegramBot.execute(new SendMessage(chatId, "Принято!"));
                    notificationTaskRepository.save(notificationTask);
                } else {
                    SendResponse response = telegramBot.execute(new SendMessage(chatId, "Время прошло. Напиши корректное время и дату."));
                }
            } else {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Напишит напоминание в формате 01.01.2022 20:00 текст."));
            };
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public static boolean checkPattern(String input) {
        String regex = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static NotificationTask createNotificationTask(Long chatId, String input) {
        String regex = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Входная строка не соответствует формату");
        }
        String time = matcher.group(1);
        System.out.println();
        LocalDateTime dateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        String text = matcher.group(3);
        return new NotificationTask(chatId, text, dateTime);

    }
}
