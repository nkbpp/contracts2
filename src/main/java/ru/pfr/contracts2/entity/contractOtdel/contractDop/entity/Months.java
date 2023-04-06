package ru.pfr.contracts2.entity.contractOtdel.contractDop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor // создания пустого конструктора
@Embeddable
public class Months {

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

    @Builder
    public Months(Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12) {
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
    }

    public Double sum() {
        return month1 + month2 + month3 + month4 + month5 + month6 + month7 + month8 + month9 + month10 + month11 + month12;
    }

}
