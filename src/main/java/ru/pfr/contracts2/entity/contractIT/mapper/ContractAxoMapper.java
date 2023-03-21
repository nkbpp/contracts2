package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.dto.ContractAxoDto;
import ru.pfr.contracts2.entity.contractIT.dto.ContractITDto;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractIT.entity.NaturalIndicator;

import java.util.ArrayList;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractAxoMapper {

    private final ItDocumentsMapper documentsMapper;
    private final NaturalIndicatorMapper naturalIndicatorMapper;

    public ContractAxoDto toDto(ContractAxo obj) {
        return ContractAxoDto.builder()
                .id(obj.getId())
                .nomGK(obj.getNomGK())
                .kontragent(obj.getKontragent())
                .dateGK(obj.getDateGK())
                .sum(obj.getSum())
                .month1(obj.getMonth1())
                .month2(obj.getMonth2())
                .month3(obj.getMonth3())
                .month4(obj.getMonth4())
                .month5(obj.getMonth5())
                .month6(obj.getMonth6())
                .month7(obj.getMonth7())
                .month8(obj.getMonth8())
                .month9(obj.getMonth9())
                .month10(obj.getMonth10())
                .month11(obj.getMonth11())
                .month12(obj.getMonth12())
                .ostatoc(obj.getSum() -
                        (obj.getMonth1() + obj.getMonth2() + obj.getMonth3() + obj.getMonth4() +
                                obj.getMonth5() + obj.getMonth6() + obj.getMonth7() + obj.getMonth8() +
                                obj.getMonth9() + obj.getMonth10() + obj.getMonth11() + obj.getMonth12()))
                .documents(obj.getItDocuments() == null ? null : obj.getItDocuments()
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
                .ostatoc(
                        (obj.getNaturalIndicators() == null || obj.getSumNaturalIndicators() == null)
                                ? null :
                                obj.getSumNaturalIndicators() - obj.getNaturalIndicators().stream()
                                        .mapToDouble(NaturalIndicator::getSum)
                                        .reduce(Double::sum).orElse(0)
                )
                .build();
    }

    public ContractAxo fromDto(ContractAxoDto dto) {
        return ContractAxo.builder()
                .id(dto.getId())
                .nomGK(dto.getNomGK())
                .kontragent(dto.getKontragent())
                .dateGK(dto.getDateGK())

                .sum(dto.getSum() == null ? 0 : dto.getSum())

                .month1(dto.getMonth1() == null ? 0 : dto.getMonth1())
                .month2(dto.getMonth2() == null ? 0 : dto.getMonth2())
                .month3(dto.getMonth3() == null ? 0 : dto.getMonth3())
                .month4(dto.getMonth4() == null ? 0 : dto.getMonth4())
                .month5(dto.getMonth5() == null ? 0 : dto.getMonth5())
                .month6(dto.getMonth6() == null ? 0 : dto.getMonth6())
                .month7(dto.getMonth7() == null ? 0 : dto.getMonth7())
                .month8(dto.getMonth8() == null ? 0 : dto.getMonth8())
                .month9(dto.getMonth9() == null ? 0 : dto.getMonth9())
                .month10(dto.getMonth10() == null ? 0 : dto.getMonth10())
                .month11(dto.getMonth11() == null ? 0 : dto.getMonth11())
                .month12(dto.getMonth12() == null ? 0 : dto.getMonth12())

                /*.budgetClassification(
                        dto.getBudgetClassification().id() == null ? null :
                                budgetClassificationRepository
                                        .findById(dto.getBudgetClassification().id())
                                        .orElse(null)
                )*/
                .sumNaturalIndicators(dto.getSumNaturalIndicators())
                .naturalIndicators(
                        dto.getNaturalIndicators() == null ? new ArrayList<>() :
                                dto.getNaturalIndicators()
                                        .stream()
                                        .map(
                                                naturalIndicatorMapper::fromDto
                                        )
                                        .toList()
                )//только для отдела AXO тут не нужен

                .build();
    }

}
