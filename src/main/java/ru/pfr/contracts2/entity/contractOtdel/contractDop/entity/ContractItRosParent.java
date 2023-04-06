package ru.pfr.contracts2.entity.contractOtdel.contractDop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@MappedSuperclass
public class ContractItRosParent extends ContractDop {

    private Integer idzirot; // IT

    private String nameot;//имя ответственного IT

    private LocalDateTime dateGKs; //действие ГК с IT

    private LocalDateTime dateGKpo; //действие ГК по IT

    //private String statusGK; //Статус ГК IT
    @Enumerated(EnumType.STRING)
    private StatusGk statusGK; //Статус ГК IT

    public ContractItRosParent(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, String documentu, Months months, User user, String role, List<DopDocuments> dopDocuments, Integer idzirot, String nameot, LocalDateTime dateGKs, LocalDateTime dateGKpo, StatusGk statusGK) {
        super(id, nomGK, kontragent, dateGK, sum, documentu, months, user, role, dopDocuments);
        this.idzirot = idzirot;
        this.nameot = nameot;
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.statusGK = statusGK;
    }

}
