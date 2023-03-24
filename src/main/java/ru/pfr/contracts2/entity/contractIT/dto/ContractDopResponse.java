package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.date.CustomDateDeserializerRuAndEn;
import ru.pfr.contracts2.entity.annotations.date.CustomDateSerializerRu;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;

import java.util.Date;
import java.util.List;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDopResponse {

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
    private String statusGK; //Статус ГК
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sum; //сумма
    private Integer idzirot; // id ответственного в зире
    private BudgetClassificationDto budgetClassification; //Бюджетная классификация

    @JsonSerialize(using = OkrugSerializer.class)
    private Double January;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double February;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double March;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double April;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double May;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double June;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double July;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double August;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double September;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double October;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double November;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double December;
    private List<DopDocumentsDto> documents;


/*    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток*/


}
