package ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.ContractItRosParent;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.Months;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("RSP")
public class ContractRsp extends ContractItRosParent {

    @Builder
    public ContractRsp(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, String documentu, Months months, User user, String role, List<DopDocuments> dopDocuments, Integer idzirot, String nameot, LocalDateTime dateGKs, LocalDateTime dateGKpo, StatusGk statusGK) {
        super(id, nomGK, kontragent, dateGK, sum, documentu, months, user, role, dopDocuments, idzirot, nameot, dateGKs, dateGKpo, statusGK);
    }

}
