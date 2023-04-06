package ru.pfr.contracts2.entity.contracts.contractDK.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.pfr.contracts2.entity.contracts.parent.entity.*;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@Entity
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("DK")
public class ContractDk extends ContractParent {

    public ContractDk(Long id, LocalDateTime receipt_date, String plat_post, Kontragent kontragent, String nomGK, LocalDateTime dateGK, String predmet_contract, VidObesp vidObesp, Double sum, LocalDateTime date_ispolnenija_GK, Integer col_days, List<Notification> notifications, Boolean ispolneno, List<MyDocuments> myDocuments, String nomerZajavkiNaVozvrat, LocalDateTime dateZajavkiNaVozvrat, User user) {
        super(id, receipt_date, plat_post, kontragent, nomGK, dateGK, predmet_contract, vidObesp, sum, date_ispolnenija_GK, col_days, notifications, ispolneno, myDocuments, nomerZajavkiNaVozvrat, dateZajavkiNaVozvrat, user);
    }

}
