package pro.sky.telegrambot.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String text;
    private LocalDateTime dateTime;

    public NotificationTask(Long chatId, String text, LocalDateTime dateTime) {
        this.chatId = chatId;
        this.text = text;
        this.dateTime = dateTime;
    }

    public NotificationTask() {

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask notificationTask = (NotificationTask) o;
        return Objects.equals(getId(), notificationTask.getId())
                && Objects.equals(getText(), notificationTask.getText())
                && Objects.equals(getChatId(), notificationTask.getChatId())
                && Objects.equals(getDateTime(),notificationTask.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getChatId(), getDateTime());
    }

//    @Override
//    public String toString() {
//        return "Faculty{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", color='" + color + '\'' +
//                '}';
//    }
}
