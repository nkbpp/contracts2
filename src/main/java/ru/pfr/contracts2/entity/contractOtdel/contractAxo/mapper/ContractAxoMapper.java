package ru.pfr.contracts2.entity.contractOtdel.contractAxo.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.dto.ContractAxoDto;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.ContractAxo;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.NaturalIndicator;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.DopDocumentsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.MonthsMapper;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractAxoMapper {

    private final DopDocumentsMapper documentsMapper;
    private final NaturalIndicatorMapper naturalIndicatorMapper;
    private final MonthsMapper monthsMapper;

    public ContractAxoDto toDto(ContractAxo obj) {
        return ContractAxoDto.builder()
                .id(obj.getId())
                .nomGK(obj.getNomGK())
                .kontragent(obj.getKontragent())
                .dateGK(obj.getDateGK())
                .sum(obj.getSum())
                .months(monthsMapper.toDto(obj.getMonths()))
                .documents(obj.getDopDocuments() == null ? null : obj.getDopDocuments()
                        .stream()
                        .map(documentsMapper::toDto)
                        .collect(Collectors.toList())
                )
                //.documentu(obj.getDocumentu())
                .sumNaturalIndicators(obj.getSumNaturalIndicators())
                .naturalIndicators(
                        obj.getNaturalIndicators() == null ? null :
                                obj.getNaturalIndicators()
                                        .stream()
                                        .map(naturalIndicatorMapper::toDto)
                                        .collect(Collectors.toList())
                )
                .factRashodMonth(
                        obj.getNaturalIndicators() == null ? null :
                                obj.getNaturalIndicators()
                                        .stream()
                                        .mapToDouble(NaturalIndicator::getSum)
                                        .sum() / obj.getNaturalIndicators().size()
                )
                .ojidRashodMonth(
                        (obj.getNaturalIndicators() == null || obj.getSumNaturalIndicators() == null)
                                ? null :
                                obj.getSumNaturalIndicators() / obj.getNaturalIndicators().size()
                )
                .ostatoc(obj.getSum() - obj.getMonths().sum())
                .ostatocNatural(
                        (obj.getNaturalIndicators() == null || obj.getSumNaturalIndicators() == null)
                                ? null :
                                obj.getSumNaturalIndicators() - obj.getNaturalIndicators().stream()
                                        .mapToDouble(NaturalIndicator::getSum)
                                        .reduce(Double::sum)
                                        .orElse(0)
                )
                .build();
    }

    public ContractAxo fromDto(@Valid ContractAxoDto dto) {

        var nat = dto.getNaturalIndicators() == null ? new ArrayList<NaturalIndicator>() :
                dto.getNaturalIndicators()
                        .stream()
                        .map(
                                naturalIndicatorMapper::fromDto
                        )
                        .toList();

        return ContractAxo.builder()
                .id(dto.getId())
                .nomGK(dto.getNomGK())
                .kontragent(dto.getKontragent())
                .dateGK(dto.getDateGK())
                .sum(dto.getSum())
                .months(monthsMapper.fromDto(dto.getMonths()))
                /*.budgetClassification(
                        dto.getBudgetClassification().id() == null ? null :
                                budgetClassificationRepository
                                        .findById(dto.getBudgetClassification().id())
                                        .orElse(null)
                )*/
                .sumNaturalIndicators(dto.getSumNaturalIndicators())
                .naturalIndicators(nat)//только для отдела AXO тут не нужен

                .build();
    }

}
