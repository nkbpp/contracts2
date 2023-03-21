package ru.pfr.contracts2.entity.contractIT.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@Entity
@Table(name = "contractit")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",
        discriminatorType = DiscriminatorType.STRING)
public class ContractDop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomGK; //номер ГК +

    private String kontragent; //Контрагент +

    private LocalDateTime dateGK; //дата ГК +

    private Double sum; //сумма +

    private Double month1; //+
    private Double month2; //+
    private Double month3; //+
    private Double month4;
    private Double month5;
    private Double month6;
    private Double month7;
    private Double month8;
    private Double month9;
    private Double month10;
    private Double month11;
    private Double month12; //+

    @Column(columnDefinition = "TEXT")
    private String documentu; //-

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user; //кто создал контракт +

    /*@ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private BudgetClassification budgetClassification;*/

    @Column(insertable = false, updatable = false)
    private String role; //+

    @LastModifiedDate
    private LocalDateTime date_update; //+

    @CreatedDate
    private LocalDateTime date_create; //+

    public String getOstatoc() {
        return MyNumbers.okrug(sum - (month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12));
    }

    public Double getOstatocDouble() {
        return sum - (month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12);
    }

    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItDocuments> itDocuments = new ArrayList<>(); // +

    public ContractDop(Long id, String nomGK, String kontragent, LocalDateTime dateGK,
                       Double sum, Double month1, Double month2, Double month3,
                       Double month4, Double month5, Double month6, Double month7,
                       Double month8, Double month9, Double month10, Double month11,
                       Double month12, User user, LocalDateTime date_update,
                       LocalDateTime date_create, List<ItDocuments> itDocuments) {
        this.id = id;
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.dateGK = dateGK;
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
        this.user = user;
        this.date_update = date_update;
        this.date_create = date_create;
        setAllDocuments(itDocuments);
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

}
