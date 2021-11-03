package ru.pfr.contracts2.entity.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
//@Table(name = "adminparam")
public class Adminparam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
    private Long id;

    private Long kolpopitok;

    private Long koefpopitok;

    private Long block;

    public Adminparam(Long kolpopitok, Long koefpopitok, Long block) {
        this.kolpopitok = kolpopitok;
        this.koefpopitok = koefpopitok;
        this.block = block;
    }
}
