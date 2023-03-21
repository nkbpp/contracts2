package ru.pfr.contracts2.entity.contractIT.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEn;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeSerializerRu;
import ru.pfr.contracts2.entity.annotations.okrug.OkrugSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContractAxoDto extends ContractDopDto {
    @JsonSerialize(using = OkrugSerializer.class)
    private Double sumNaturalIndicators;
    private List<NaturalIndicatorDto> naturalIndicators;

    @JsonSerialize(using = OkrugSerializer.class)
    private Double factRashodMonth;

    @JsonSerialize(using = OkrugSerializer.class)
    private Double ojidRashodMonth;

    @JsonSerialize(using = OkrugSerializer.class)
    private Double ostatoc;

    @Builder
    public ContractAxoDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double month1, Double month2, Double month3, Double month4, Double month5, Double month6, Double month7, Double month8, Double month9, Double month10, Double month11, Double month12, Double ostatoc, List<ItDocumentsDto> documents, Double sumNaturalIndicators, List<NaturalIndicatorDto> naturalIndicators, Double factRashodMonth, Double ojidRashodMonth, Double ostatoc1) {
        super(id, nomGK, kontragent, dateGK, sum, month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12, ostatoc, documents);
        this.sumNaturalIndicators = sumNaturalIndicators;
        this.naturalIndicators = naturalIndicators;
        this.factRashodMonth = factRashodMonth;
        this.ojidRashodMonth = ojidRashodMonth;
        this.ostatoc = ostatoc1;
    }
}
