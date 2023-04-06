package ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.ContractDop;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.Months;
import ru.pfr.contracts2.entity.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@Entity
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("AXO")
public class ContractAxo extends ContractDop {

    private Double sumNaturalIndicators; //Axo

    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NaturalIndicator> naturalIndicators = new ArrayList<>(); //AXO

    @Builder
    public ContractAxo(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, String documentu, Months months, User user, String role, List<DopDocuments> dopDocuments, Double sumNaturalIndicators, List<NaturalIndicator> naturalIndicators) {
        super(id, nomGK, kontragent, dateGK, sum, documentu, months, user, role, dopDocuments);
        this.sumNaturalIndicators = sumNaturalIndicators;
        setAllNaturalIndicators(naturalIndicators);
    }

    //addNaturalIndicators
    public void addNaturalIndicators(NaturalIndicator naturalIndicator) {
        this.naturalIndicators.add(naturalIndicator);
        naturalIndicator.setContractIT(this);
    }

    public void setAllNaturalIndicators(List<NaturalIndicator> naturalIndicators) {
        while (this.naturalIndicators.size() > 0) { //удаляем все что было
            removeNaturalIndicators(this.naturalIndicators.get(0));
        }
        for (NaturalIndicator d :
                naturalIndicators) {
            addNaturalIndicators(d);
        }
    }

    public void removeNaturalIndicators(NaturalIndicator naturalIndicator) {
        this.naturalIndicators.remove(naturalIndicator);
        naturalIndicator.setContractIT(null);
    }

}
