package ru.pfr.contracts2.entity.contractIT.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("IT")
public class ContractIT extends ContractDop {

    private Integer idzirot; // IT

    private String nameot;//имя ответственного IT

    private LocalDateTime dateGKs; //действие ГК с IT

    private LocalDateTime dateGKpo; //действие ГК по IT

    private String statusGK; //Статус ГК IT

    @Builder
    public ContractIT(Long id, String nomGK, String kontragent, LocalDateTime dateGK,
                      Double sum, Double month1, Double month2, Double month3,
                      Double month4, Double month5, Double month6, Double month7,
                      Double month8, Double month9, Double month10, Double month11,
                      Double month12, User user, List<DopDocuments> dopDocuments,
                      Integer idzirot, String nameot, LocalDateTime dateGKs,
                      LocalDateTime dateGKpo, String statusGK) {
        super(id, nomGK, kontragent, dateGK, sum, month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12, user, dopDocuments);
        this.idzirot = idzirot;
        this.nameot = nameot;
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.statusGK = statusGK;
    }

}
