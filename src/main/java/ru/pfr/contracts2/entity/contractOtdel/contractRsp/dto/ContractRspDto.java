package ru.pfr.contracts2.entity.contractOtdel.contractRsp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractItRspDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.DopDocumentsDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.MonthsDto;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ContractRspDto extends ContractItRspDto {

    @Builder
    public ContractRspDto(Long id, String nomGK, String kontragent, LocalDateTime dateGK, Double sum, Double ostatoc, List<DopDocumentsDto> documents, MonthsDto months, LocalDateTime dateGKs, LocalDateTime dateGKpo, String statusGK, Integer idzirot, String nameot) {
        super(id, nomGK, kontragent, dateGK, sum, ostatoc, documents, months, dateGKs, dateGKpo, statusGK, idzirot, nameot);
    }
}
