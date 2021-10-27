package ru.pfr.contracts2.entity.contracts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
// 	генерация всех служебных методов, заменяет сразу команды @ToString, @EqualsAndHashCode, Getter, Setter, @RequiredArgsConstructor
@NoArgsConstructor // создания пустого конструктора
@AllArgsConstructor // конструктора включающего все возможные поля
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date receipt_date; //дата поступления

    private String name_koltr; //наименование контрагента ???

    private String nomGK; //номер ГК

    private Date dateGK; //дата ГК

    private String predmet_contract; //краткое содержание предмета контракта

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private VidObesp vidObesp; //вид обеспечения

    private Float sum; //сумма

    private Date date_ispolnenija_GK; //дата исполнения ГК

    private Integer col_days; //Условия возврата ГК (количество дней от исполнения)

    private Date raschet_date; //Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Notification> notifications;//кого оповестить

    private Boolean ispolneno; //Отметка об исполнении

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MyDocuments> myDocuments;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private User user; //кто создал контракт

    private Date date_update;

    private Date date_create;

    public String getReceipt_dateRu() {
        return ConverterDate.datetostring_ddMMyyyy(receipt_date);
    }

    public String getReceipt_dateEn() {
        return ConverterDate.datetostring_yyyyMMdd(receipt_date);
    }

    public String getDateGKRu() {
        return ConverterDate.datetostring_ddMMyyyy(dateGK);
    }
    public String getDateGKEn() {
        return ConverterDate.datetostring_yyyyMMdd(dateGK);
    }

    public String getDate_ispolnenija_GKRu() {
        return ConverterDate.datetostring_ddMMyyyy(date_ispolnenija_GK);
    }
    public String getDate_ispolnenija_GKEn() {
        return ConverterDate.datetostring_yyyyMMdd(date_ispolnenija_GK);
    }

    public String getRaschet_dateRu() {
        return ConverterDate.datetostring_ddMMyyyy(raschet_date);
    }
    public String getRaschet_dateEn() {
        return ConverterDate.datetostring_yyyyMMdd(raschet_date);
    }


    public String getSumOk() {
        return MyNumbers.okrug(sum);
    }

    public Contract(Date receipt_date, String name_koltr, String nomGK, Date dateGK, String predmet_contract, VidObesp vidObesp, Float sum, Date date_ispolnenija_GK, Integer col_days, List<Notification> notifications, Boolean ispolneno, List<MyDocuments> myDocuments, User user) {
        this.receipt_date = receipt_date;
        this.name_koltr = name_koltr;
        this.nomGK = nomGK;
        this.dateGK = dateGK;
        this.predmet_contract = predmet_contract;
        this.vidObesp = vidObesp;
        this.sum = sum;
        this.date_ispolnenija_GK = date_ispolnenija_GK;
        this.col_days = col_days;

        this.notifications = notifications;
        this.ispolneno = ispolneno;
        this.myDocuments = myDocuments;
        this.user = user;

        date_create = new Date();
    }
}
