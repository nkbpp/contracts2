package ru.pfr.contracts2.entity.contracts.contractRsp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@Entity
public class KontragentRsp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String inn;

    public KontragentRsp(String name, String inn) {
        this.name = name;
        this.inn = inn;
    }

    @Builder
    public KontragentRsp(Long id, String name, String inn) {
        this.id = id;
        this.name = name;
        this.inn = inn;
    }

    public String getNameInn() {
        return name + " " + inn;
    }
}
