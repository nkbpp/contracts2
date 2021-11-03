package ru.pfr.contracts2.entity.contracts;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.util.ArrayList;
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

    private String plat_post; //Платежное поручение

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Kontragent kontragent;

    //private String name_koltr; //наименование контрагента ???

    private String nomGK; //номер ГК

    private Date dateGK; //дата ГК

    private String predmet_contract; //краткое содержание предмета контракта

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private VidObesp vidObesp; //вид обеспечения

    private Float sum; //сумма

    private Date date_ispolnenija_GK; //дата исполнения ГК

    private Integer col_days; //Условия возврата ГК (количество дней от исполнения)

    private Date raschet_date; //Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)

    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();//кого оповестить

    private Boolean ispolneno; //Отметка об исполнении

    private String nomerZajavkiNaVozvrat; //Номер заявки на возврат

    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyDocuments> myDocuments = new ArrayList<>();
    ;

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

    public Contract(Date receipt_date, String plat_post, Kontragent kontragent/*String name_koltr*/, String nomGK,
                    Date dateGK, String predmet_contract, VidObesp vidObesp, Float sum,
                    Date date_ispolnenija_GK, Integer col_days, List<Notification> notifications,
                    Boolean ispolneno, List<MyDocuments> myDocuments, String nomerZajavkiNaVozvrat, User user) {
        this.receipt_date = receipt_date;
        this.plat_post = plat_post;
        //this.name_koltr = name_koltr;
        this.kontragent = kontragent;
        this.nomGK = nomGK;
        this.dateGK = dateGK;
        this.predmet_contract = predmet_contract;
        this.vidObesp = vidObesp;
        this.sum = sum;
        this.date_ispolnenija_GK = date_ispolnenija_GK;
        this.col_days = col_days;


        this.ispolneno = ispolneno;
        this.nomerZajavkiNaVozvrat = nomerZajavkiNaVozvrat;

        setAllNotification(notifications);
        setAllDocuments(myDocuments);
        this.user = user;

        date_create = new Date();
    }

    public void addDocuments(MyDocuments myDoc) {
        this.myDocuments.add(myDoc);
        myDoc.setContract(this);
    }

    public void setAllDocuments(List<MyDocuments> myDocs) {
        for (MyDocuments d :
                myDocs) {
            addDocuments(d);
        }
    }

    public void removeDocuments(MyDocuments myDoc) {
        this.myDocuments.remove(myDoc);
        myDoc.setContract(null);
    }

    public void addNotification(Notification notif) {
        this.notifications.add(notif);
        notif.setContract(this);
    }

    public void setAllNotification(List<Notification> notif) {
        while (notifications.size()>0){
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

}
