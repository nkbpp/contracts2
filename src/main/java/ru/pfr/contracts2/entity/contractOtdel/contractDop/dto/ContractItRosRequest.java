package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.numbers.CustomDoubleDeserializerNotNull;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;
import ru.pfr.contracts2.entity.annotations.valid.StatusGKValid;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ContractItRosRequest {

    private Long id;
    @NotBlank
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

    //@Pattern(regexp = "^(Действующий)|(Исполнен)|(Расторгнут)|(^$)$")
    @StatusGKValid
    private String statusGK; //Статус ГК
    @NotNull
    @PositiveOrZero
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double sum; //сумма

    private Integer idzirot; // id ответственного в зире
    //private BudgetClassificationDto budgetClassification; //Бюджетная классификация

    @Valid
    private MonthsDto months;

    @PositiveOrZero
    @Max(value = 31)
    private Integer dayNotification; //когда присылать уведомления

    @Builder
    public ContractItRosRequest(Long id, String nomGK, String kontragent, LocalDateTime dateGK, LocalDateTime dateGKs, LocalDateTime dateGKpo, String statusGK, Double sum, Integer idzirot, MonthsDto months, Integer dayNotification) {
        this.id = id;
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.dateGK = dateGK;
        this.dateGKs = dateGKs;
        this.dateGKpo = dateGKpo;
        this.statusGK = statusGK;
        this.sum = sum;
        this.idzirot = idzirot;
        this.months = months;
        this.dayNotification = dayNotification;
    }
}
