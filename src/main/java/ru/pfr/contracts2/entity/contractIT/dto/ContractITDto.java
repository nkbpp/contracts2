package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.date.CustomDateDeserializerRuAndEn;
import ru.pfr.contracts2.entity.annotations.date.CustomDateSerializerRu;
import ru.pfr.contracts2.entity.annotations.okrug.OkrugSerializer;

import java.util.Date;
import java.util.List;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractITDto {

    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGK; //дата ГК
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGKs; //действие ГК с
    @JsonDeserialize(using = CustomDateDeserializerRuAndEn.class)
    @JsonSerialize(using = CustomDateSerializerRu.class)
    private Date dateGKpo; //действие ГК по
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
    private String statusGK; //Статус ГК
    private Integer idzirot; // id ответственного в зире
    private String nameot;//имя ответственного
    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток
    private List<ItDocumentsDto> documents;

    private BudgetClassificationDto budgetClassification;

}
