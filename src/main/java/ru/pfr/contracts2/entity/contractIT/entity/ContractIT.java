package ru.pfr.contracts2.entity.contractIT.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
public class ContractIT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomGK; //номер ГК

    private String kontragent; //Контрагент

    private LocalDateTime dateGK; //дата ГК

    private LocalDateTime dateGKs; //действие ГК с
    private LocalDateTime dateGKpo; //действие ГК по

    private String statusGK; //Статус ГК
    private Double sum; //сумма

    private Double month1;
    private Double month2;
    private Double month3;
    private Double month4;
    private Double month5;
    private Double month6;
    private Double month7;
    private Double month8;
    private Double month9;
    private Double month10;
    private Double month11;
    private Double month12;

    @Column(columnDefinition = "TEXT")
    private String documentu;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user; //кто создал контракт

    /*@ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private BudgetClassification budgetClassification;*/

    private Integer idzirot;
    private String nameot;//имя ответственного
    private String role;

    @LastModifiedDate
    private LocalDateTime date_update;

    @CreatedDate
    private LocalDateTime date_create;

    public String getOstatoc() {
        return MyNumbers.okrug(sum - (month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12));
    }

    public Double getOstatocDouble() {
        return sum - (month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12);
    }

    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItDocuments> itDocuments = new ArrayList<>();

    private Double sumNaturalIndicators;

    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NaturalIndicator> naturalIndicators = new ArrayList<>();

    @Builder
    public ContractIT(Long id, String nomGK, String kontragent, String statusGK,
                      LocalDateTime dateGK, LocalDateTime dateGKs, LocalDateTime dateGKpo, Double sum, Double month1, Double month2,
                      Double month3, Double month4, Double month5, Double month6, Double month7,
                      Double month8, Double month9, Double month10, Double month11, Double month12,
                      Double sumNaturalIndicators, List<NaturalIndicator> naturalIndicators,
                      String documentu, List<ItDocuments> itDocuments, User user, String role,
                      Integer idzirot, String nameot/*, BudgetClassification budgetClassification*/) {
        this.id = id;
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.statusGK = statusGK;
        this.dateGK = dateGK;
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.idzirot = idzirot;
        this.nameot = nameot;
        this.sum = sum;
        this.month1 = month1;
        this.month2 = month2;
        this.month3 = month3;
        this.month4 = month4;
        this.month5 = month5;
        this.month6 = month6;
        this.month7 = month7;
        this.month8 = month8;
        this.month9 = month9;
        this.month10 = month10;
        this.month11 = month11;
        this.month12 = month12;
        this.documentu = documentu;
        this.user = user;
        /*this.budgetClassification = budgetClassification;*/

        this.sumNaturalIndicators = sumNaturalIndicators;
        setAllNaturalIndicators(naturalIndicators);

        setAllDocuments(itDocuments);

        this.role = role;

    }

    public void addDocuments(ItDocuments myDoc) {
        this.itDocuments.add(myDoc);
        myDoc.setContractIT(this);
    }

    public void setAllDocuments(List<ItDocuments> myDocs) {
/*        while (itDocuments.size()>0){
            removeDocuments(itDocuments.get(0));
        }*/
        if (myDocs != null) {
            for (ItDocuments d :
                    myDocs) {
                addDocuments(d);
            }
        }

    }

    public void removeDocuments(ItDocuments myDoc) {
        this.itDocuments.remove(myDoc);
        myDoc.setContractIT(null);
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

    public String getSumNaturalIndicatorsStr() {
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
    }

    public String getOstatocNaturalIndicator() {
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
    }

}
