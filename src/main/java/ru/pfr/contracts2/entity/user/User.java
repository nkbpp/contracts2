package ru.pfr.contracts2.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private Long active;

    private Date date_last_entry; //дата последней попытки входа

    private Long id_user_zir;

    private Long id_ondel_zir;

    private String name_ondel_zir;

    private String email;

    private Date date_update;

    private Date date_create;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    Rayon rayon;

    public User(String login, Rayon rayon) {
        this.login = login;
        this.active = 1L;
        this.date_last_entry = new Date();
        this.date_update = new Date();
        this.date_create = new Date();
        this.rayon = rayon;
    }


}
