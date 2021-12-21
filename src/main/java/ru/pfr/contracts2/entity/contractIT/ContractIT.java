package ru.pfr.contracts2.entity.contractIT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.MyNumbers;

import javax.persistence.*;
import java.util.Date;

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

    private Date dateGK; //дата ГК

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

    private Date date_update;

    private Date date_create;

    public String getDateGKRu() {
        return dateGK==null?"":ConverterDate.datetostring_ddMMyyyy(dateGK);
    }

    public String getDateGKEn() {
        return dateGK==null?"":ConverterDate.datetostring_yyyyMMdd(dateGK);
    }

    public String getSumOk() {
        return MyNumbers.okrug(sum);
    }

    public String getMonth1Ok() {
        return MyNumbers.okrug(month1);
    }

    public String getMonth2Ok() {
        return MyNumbers.okrug(month2);
    }

    public String getMonth3Ok() {
        return MyNumbers.okrug(month3);
    }

    public String getMonth4Ok() {
        return MyNumbers.okrug(month4);
    }

    public String getMonth5Ok() {
        return MyNumbers.okrug(month5);
    }

    public String getMonth6Ok() {
        return MyNumbers.okrug(month6);
    }

    public String getMonth7Ok() {
        return MyNumbers.okrug(month7);
    }

    public String getMonth8Ok() {
        return MyNumbers.okrug(month8);
    }

    public String getMonth9Ok() {
        return MyNumbers.okrug(month9);
    }

    public String getMonth10Ok() {
        return MyNumbers.okrug(month10);
    }

    public String getMonth11Ok() {
        return MyNumbers.okrug(month11);
    }

    public String getMonth12Ok() {
        return MyNumbers.okrug(month12);
    }

    public String getOstatoc() {
        return MyNumbers.okrug(sum - (month1+month2+month3+month4+month5+month6+month7+month8+month9+month10+month11+month12));
    }

    public ContractIT(String nomGK, Date dateGK, Double sum, Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12, String documentu, User user) {
        this.nomGK = nomGK;
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
        this.documentu = documentu;
        this.user = user;

        date_create = new Date();
    }

}
