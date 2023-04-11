package ru.pfr.contracts2.entity.contracts.contractDK.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.pfr.contracts2.entity.contracts.parent.entity.*;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.*;
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

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Kontragent kontragent;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private VidObesp vidObesp; //вид обеспечения

    public ContractDk(Long id, LocalDateTime receipt_date, String plat_post, String nomGK, LocalDateTime dateGK, String predmet_contract, Double sum, LocalDateTime date_ispolnenija_GK, Integer col_days, List<Notification> notifications, Boolean ispolneno, List<MyDocuments> myDocuments, String nomerZajavkiNaVozvrat, LocalDateTime dateZajavkiNaVozvrat, User user, Kontragent kontragent, VidObesp vidObesp) {
        super(id, receipt_date, plat_post, nomGK, dateGK, predmet_contract, sum, date_ispolnenija_GK, col_days, notifications, ispolneno, myDocuments, nomerZajavkiNaVozvrat, dateZajavkiNaVozvrat, user);
        this.kontragent = kontragent;
        this.vidObesp = vidObesp;
    }

}
