package ru.pfr.contracts2.entity.contractOtdel.contractDop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.contracts2.entity.AuditEntity;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor

@Entity
@Table(name = "contractit")
@NoArgsConstructor // создания пустого конструктора
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",
        discriminatorType = DiscriminatorType.STRING)
public abstract class ContractDop extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    private LocalDateTime dateGK; //дата ГК
    private Double sum; //сумма
    @Column(columnDefinition = "TEXT")
    private String documentu; //возможно удалить потом
    @Embedded
    private Months months;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user; //кто создал контракт

    /*@ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private BudgetClassification budgetClassification;*/

    @Column(insertable = false, updatable = false)
    private String role; //+

    public String getOstatoc() {
        return MyNumbers.okrug(sum - months.sum());
    }

    @OneToMany(mappedBy = "contractIT", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DopDocuments> dopDocuments = new ArrayList<>(); // +

    public ContractDop(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, String documentu, Months months, User user, String role, List<DopDocuments> dopDocuments) {
        this.id = id;
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.dateGK = dateGK;
        this.sum = sum;
        this.documentu = documentu;
        this.months = months;
        this.user = user;
        this.role = role;
        setAllDocuments(dopDocuments);
    }

    public void addDocuments(DopDocuments myDoc) {
        this.dopDocuments.add(myDoc);
        myDoc.setContractIT(this);
    }

    public void setAllDocuments(List<DopDocuments> myDocs) {
        if (myDocs != null) {
            for (DopDocuments d :
                    myDocs) {
                addDocuments(d);
            }
        }
    }

    public void removeDocuments(DopDocuments myDoc) {
        this.dopDocuments.remove(myDoc);
        myDoc.setContractIT(null);
    }

}
