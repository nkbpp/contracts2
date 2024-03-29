package ru.pfr.contracts2.entity.contracts.parent.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import ru.pfr.contracts2.entity.AuditEntity;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor // создания пустого конструктора
@Entity
@Table(name = "contract")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role",
        discriminatorType = DiscriminatorType.STRING)
public class ContractParent extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime receipt_date; //дата поступления

    private String plat_post; //Платежное поручение

    private String nomGK; //номер ГК

    private LocalDateTime dateGK; //дата ГК

    @Type(type = "text")
    private String predmet_contract; //краткое содержание предмета контракта

    private Double sum; //сумма

    private LocalDateTime date_ispolnenija_GK; //дата исполнения ГК

    private Integer col_days; //Условия возврата ГК (количество дней от исполнения)

    private LocalDateTime raschet_date; //Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)

    @Builder.Default
    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();//кого оповестить

    private Boolean ispolneno; //Отметка об исполнении

    private String nomerZajavkiNaVozvrat; //Номер заявки на возврат

    private LocalDateTime dateZajavkiNaVozvrat; //Номер заявки на возврат

    @Builder.Default
    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyDocuments> myDocuments = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user; //кто создал контракт

    public String getSumOk() {
        return MyNumbers.okrug(sum);
    }

    @Column(insertable = false, updatable = false)
    private String role;


    public ContractParent(Long id, LocalDateTime receipt_date, String plat_post,
                          String nomGK,
                          LocalDateTime dateGK, String predmet_contract, Double sum,
                          LocalDateTime date_ispolnenija_GK, Integer col_days, List<Notification> notifications,
                          Boolean ispolneno, List<MyDocuments> myDocuments, String nomerZajavkiNaVozvrat,
                          LocalDateTime dateZajavkiNaVozvrat, User user) {
        this.id = id;
        this.receipt_date = receipt_date;
        this.plat_post = plat_post;
        this.nomGK = nomGK;
        this.dateGK = dateGK;
        this.predmet_contract = predmet_contract;
        this.sum = sum;
        this.date_ispolnenija_GK = date_ispolnenija_GK;
        this.col_days = col_days;


        this.ispolneno = ispolneno;
        this.nomerZajavkiNaVozvrat = nomerZajavkiNaVozvrat;
        this.dateZajavkiNaVozvrat = dateZajavkiNaVozvrat;
        this.notifications = notifications;
        this.myDocuments = myDocuments;

        /*setAllNotification(notifications);
        setAllDocuments(myDocuments);*/
        this.user = user;
    }

    public void addDocuments(MyDocuments myDoc) {
        this.myDocuments.add(myDoc);
        myDoc.setContract(this);
    }

    public void setAllDocuments(List<MyDocuments> myDocs) {
        if (myDocs != null) {
            for (MyDocuments d :
                    myDocs) {
                addDocuments(d);
            }
        }
    }

    public void addNotification(Notification notif) {
        this.notifications.add(notif);
        notif.setContract(this);
    }

    public void setAllNotification(List<Notification> notif) {
        while (notifications.size() > 0) {
            removeNotification(notifications.get(0));
        }
        for (Notification n :
                notif) {
            addNotification(n);
        }
    }

    public void removeNotification(Notification notif) {
        this.notifications.remove(notif);
        notif.setContract(null);
    }


    public long getDaysOst() { //дней осталось
        if (raschet_date != null) {
            LocalDate ras = raschet_date.toLocalDate();
            LocalDate tec = LocalDate.now();
            return ChronoUnit.DAYS.between(tec, ras);
        }
        return -1;
    }

    public long getDayVsego() { //дней всего
        if (raschet_date != null && dateGK != null) {
            LocalDate ras = raschet_date.toLocalDate();
            LocalDate tec = dateGK.toLocalDate();
            return ChronoUnit.DAYS.between(tec, ras);
            //return ConverterDate.differenceInDays(ras, tec);
        }
        return -1;
    }

    public long getProcent() { //дней всего
        return 100 * (getDayVsego() - getDaysOst()) / getDayVsego();
    }

}
