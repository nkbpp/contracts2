package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.okrug.OkrugSerializer;

import java.time.LocalDateTime;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDopRequest {

    private Long id;
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGK; //дата ГК
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKs; //действие ГК с
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGKpo; //действие ГК по
    private String statusGK; //Статус ГК
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sum; //сумма
    private Integer idzirot; // id ответственного в зире
    //private BudgetClassificationDto budgetClassification; //Бюджетная классификация
    @JsonSerialize(using = OkrugSerializer.class)
    private Double january;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double february;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double march;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double april;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double may;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double june;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double july;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double august;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double september;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double october;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double november;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double december;

}
