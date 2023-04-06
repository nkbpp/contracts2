package ru.pfr.contracts2.entity.contractOtdel.contractAxo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.numbers.OkrugSerializer;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractDopDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.DopDocumentsDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.MonthsDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContractAxoDto extends ContractDopDto {
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sumNaturalIndicators;
    @Valid
    private List<NaturalIndicatorDto> naturalIndicators;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double factRashodMonth;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double ojidRashodMonth;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatocNatural;

    @Builder
    public ContractAxoDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double ostatoc, List<DopDocumentsDto> documents, MonthsDto months, Double sumNaturalIndicators, List<NaturalIndicatorDto> naturalIndicators, Double factRashodMonth, Double ojidRashodMonth, Double ostatocNatural) {
        super(id, nomGK, kontragent, dateGK, sum, ostatoc, documents, months);
        this.sumNaturalIndicators = sumNaturalIndicators;
        this.naturalIndicators = naturalIndicators;
        this.factRashodMonth = factRashodMonth;
        this.ojidRashodMonth = ojidRashodMonth;
        this.ostatocNatural = ostatocNatural;
    }
}
