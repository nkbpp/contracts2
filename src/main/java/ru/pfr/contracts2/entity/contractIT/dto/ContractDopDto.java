package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEn;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.okrug.OkrugSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
public class ContractDopDto {

    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGK; //дата ГК
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sum; //сумма
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month1;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month2;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month3;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month4;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month5;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month6;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month7;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month8;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month9;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month10;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month11;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double month12;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток
    private List<ItDocumentsDto> documents;

    /*private BudgetClassificationDto budgetClassification;*/

    public ContractDopDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12, Double ostatoc, List<ItDocumentsDto> documents) {
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
        this.ostatoc = ostatoc;
        this.documents = documents;
    }
}
