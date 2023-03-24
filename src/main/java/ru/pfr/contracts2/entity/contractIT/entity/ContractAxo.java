package ru.pfr.contracts2.entity.contractIT.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
    public ContractAxo(Long id, String nomGK, String kontragent, LocalDateTime dateGK,
                       Double sum, Double month1, Double month2, Double month3,
                       Double month4, Double month5, Double month6, Double month7,
                       Double month8, Double month9, Double month10, Double month11,
                       Double month12, User user, List<DopDocuments> dopDocuments,
                       Double sumNaturalIndicators, List<NaturalIndicator> naturalIndicators) {
        super(id, nomGK, kontragent, dateGK, sum, month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12, user, dopDocuments);
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

    /*public String getSumNaturalIndicatorsStr() {
        return MyNumbers.okrug(sumNaturalIndicators);
    }

    public String getNaturalIndicatorsStr() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < naturalIndicators.size(); i++) {
            s.append(naturalIndicators.get(i).getSumOk()).append((i + 1) != naturalIndicators.size() ? ";" : "");
        }

        return s.toString();
    }

    public String getNaturalIndicatorsById(int i) {
        String s = "";

        try {
            s = naturalIndicators.get(i).getSumOk();
        } catch (Exception e) {
            s = null;
        }

        return s;
    }*/

    /*public String getOstatocNaturalIndicator() {
        return MyNumbers.okrug(sumNaturalIndicators - naturalIndicators.stream()
                .mapToDouble(value -> value.getSum())
                .reduce((x, y) -> x + y).orElse(0));
    }

    public String getOjidRashodMonth() {
        return MyNumbers.okrug(sumNaturalIndicators / naturalIndicators.size());
    }

    public String getFactRashodMonth() {
        return MyNumbers.okrug(naturalIndicators.stream()
                .mapToDouble(value -> value.getSum())
                .sum() / naturalIndicators.size()
        );
    }*/

}
