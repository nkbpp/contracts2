package ru.pfr.contracts2.entity.contractOtdel.contractDop.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.numbers.CustomDoubleDeserializerNotNull;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ContractDopDto {

    private Long id;
    @NotBlank
    private String nomGK; //номер ГК
    private String kontragent; //Контрагент
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime dateGK; //дата ГК
    @NotNull
    @JsonSerialize(using = OkrugSerializer.class)
    @JsonDeserialize(using = CustomDoubleDeserializerNotNull.class)
    private Double sum; //сумма

    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc; //остаток
    private List<DopDocumentsDto> documents;

    @Valid
    private MonthsDto months;

    /*private BudgetClassificationDto budgetClassification;*/

    public ContractDopDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double ostatoc, List<DopDocumentsDto> documents, MonthsDto months) {
        this.id = id;
        this.nomGK = nomGK;
        this.kontragent = kontragent;
        this.dateGK = dateGK;
        this.sum = sum;
        this.ostatoc = ostatoc;
        this.documents = documents;
        this.months = months;
    }
}
