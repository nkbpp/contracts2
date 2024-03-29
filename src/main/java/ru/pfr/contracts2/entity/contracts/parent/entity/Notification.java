package ru.pfr.contracts2.entity.contracts.parent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
@Builder
//@Table(name = "notification")
public class Notification { //уведомление
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_user; //id в зире

    private String name; //id в зире

    @ManyToOne(fetch = FetchType.LAZY)
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "contract_id")
    private ContractParent contract;

    @JsonIgnore
    public ContractParent getContract() {
        return contract;
    }

    public Notification(Long id_user, String name) {
        this.id_user = id_user;
        this.name = name;
    }
}
