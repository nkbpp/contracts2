package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contractIT.dto.ContractAxoDto;
import ru.pfr.contracts2.entity.contractIT.dto.ContractITDto;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractAxoMapper {

    private final ItDocumentsMapper documentsMapper;
    private final NaturalIndicatorMapper naturalIndicatorMapper;

    public ContractAxoDto toDto(ContractIT obj) {
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
                .documentu(obj.getDocumentu())
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
                                        .mapToDouble(value -> value.getSum())
                                        .sum() / obj.getNaturalIndicators().size())
                .ojidRashodMonth(
                        obj.getNaturalIndicators() == null ? null :
                                obj.getSumNaturalIndicators() / obj.getNaturalIndicators().size())
                .ostatoc(
                        obj.getNaturalIndicators() == null ? null :
                        obj.getSumNaturalIndicators() - obj.getNaturalIndicators().stream()
                                .mapToDouble(value -> value.getSum())
                                .reduce((x,y) -> x + y).orElse(0)
                )
                .build();
    }

    public ContractIT fromDto(ContractITDto dto) {
        return ContractIT.builder()
/*                .id(dto.getId())
                .name(dto.getNomGK())
                .name(dto.getKontragent())*/
                .build();
    }

}
