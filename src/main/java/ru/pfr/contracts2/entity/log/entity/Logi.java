package ru.pfr.contracts2.entity.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
public class Logi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private String user;

    private String type;
    @Column(columnDefinition = "TEXT")
    private String text;

    public Logi(String user, String text) {
        date = new Date();
        this.type = "Info";
        this.user = user;
        this.text = text;
    }

    @Builder
    public Logi(String user, String type, String text) {
        this(user, text);
        this.type = type;
    }

    public String getDateStr() {
        //return "sdf";
        return date == null ? "" : date.toString();
    }
}
