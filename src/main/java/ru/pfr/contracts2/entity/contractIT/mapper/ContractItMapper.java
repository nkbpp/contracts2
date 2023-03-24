package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.dto.ContractDopRequest;
import ru.pfr.contracts2.entity.contractIT.dto.ContractITDto;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractItMapper {

    private final DopDocumentsMapper documentsMapper;
    /*private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;
    private final BudgetClassificationRepository budgetClassificationRepository;*/

    private final ZirServise zirServise;

    private final ContractItService contractItService;

    public ContractITDto toDto(ContractIT obj) {
        return ContractITDto.builder()
                .id(obj.getId())
                .nomGK(obj.getNomGK())
                .kontragent(obj.getKontragent())
                .dateGK(obj.getDateGK())
                .dateGKs(obj.getDateGKs())
                .dateGKpo(obj.getDateGKpo())
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
                .idzirot(obj.getIdzirot())
                .nameot(obj.getNameot())
                .statusGK(obj.getStatusGK())
                .ostatoc(obj.getSum() -
                        (obj.getMonth1() + obj.getMonth2() + obj.getMonth3() + obj.getMonth4() +
                                obj.getMonth5() + obj.getMonth6() + obj.getMonth7() + obj.getMonth8() +
                                obj.getMonth9() + obj.getMonth10() + obj.getMonth11() + obj.getMonth12()))
                .documents(obj.getDopDocuments() == null ? null : obj.getDopDocuments()
                        .stream()
                        .map(documentsMapper::toDto)
                        .collect(Collectors.toList())
                )
                /*.budgetClassification(
                        obj.getBudgetClassification() == null ?
                                null :
                                budgetClassificationResponseMapper.apply(obj.getBudgetClassification())
                )*/
                .build();
    }

    public ContractIT fromContractDopRequest(ContractDopRequest dto) {

        String fio = "";
        try {
            fio = zirServise.getNameUserById(dto.getIdzirot());
        } catch (Exception e) {
        }

        if (dto.getId() != null) {
            ContractIT contractIT = contractItService.findById(dto.getId());
            contractIT.setNomGK(dto.getNomGK());
            contractIT.setKontragent(dto.getKontragent());
            contractIT.setDateGK(dto.getDateGK());
            contractIT.setDateGKs(dto.getDateGKs());
            contractIT.setDateGKpo(dto.getDateGKpo());
            contractIT.setStatusGK(dto.getStatusGK());
            contractIT.setSum(dto.getSum());
            contractIT.setMonth1(dto.getMonth1());
            contractIT.setMonth2(dto.getMonth2());
            contractIT.setMonth3(dto.getMonth3());
            contractIT.setMonth4(dto.getMonth4());
            contractIT.setMonth5(dto.getMonth5());
            contractIT.setMonth6(dto.getMonth6());
            contractIT.setMonth7(dto.getMonth7());
            contractIT.setMonth8(dto.getMonth8());
            contractIT.setMonth9(dto.getMonth9());
            contractIT.setMonth10(dto.getMonth10());
            contractIT.setMonth11(dto.getMonth11());
            contractIT.setMonth12(dto.getMonth12());
            contractIT.setIdzirot(dto.getIdzirot());
            contractIT.setNameot(fio);
            return contractIT;
        }

        return ContractIT.builder()

                .id(dto.getId())
                .nomGK(dto.getNomGK())
                .kontragent(dto.getKontragent())
                .dateGK(dto.getDateGK())
                .dateGKs(dto.getDateGKs())
                .dateGKpo(dto.getDateGKpo())
                .statusGK(dto.getStatusGK())
                .sum(dto.getSum())

                .month1(dto.getMonth1())
                .month2(dto.getMonth2())
                .month3(dto.getMonth3())
                .month4(dto.getMonth4())
                .month5(dto.getMonth5())
                .month6(dto.getMonth6())
                .month7(dto.getMonth7())
                .month8(dto.getMonth8())
                .month9(dto.getMonth9())
                .month10(dto.getMonth10())
                .month11(dto.getMonth11())
                .month12(dto.getMonth12())
                /*.month1(dto.getJanuary() == null ? 0 : dto.getJanuary())
                .month2(dto.getFebruary() == null ? 0 : dto.getFebruary())
                .month3(dto.getMarch() == null ? 0 : dto.getMarch())
                .month4(dto.getApril() == null ? 0 : dto.getApril())
                .month5(dto.getMay() == null ? 0 : dto.getMay())
                .month6(dto.getJune() == null ? 0 : dto.getJune())
                .month7(dto.getJuly() == null ? 0 : dto.getJuly())
                .month8(dto.getAugust() == null ? 0 : dto.getAugust())
                .month9(dto.getSeptember() == null ? 0 : dto.getSeptember())
                .month10(dto.getOctober() == null ? 0 : dto.getOctober())
                .month11(dto.getNovember() == null ? 0 : dto.getNovember())
                .month12(dto.getDecember() == null ? 0 : dto.getDecember())*/

                /*.budgetClassification(
                        dto.getBudgetClassification().id() == null ? null :
                                budgetClassificationRepository
                                        .findById(dto.getBudgetClassification().id())
                                        .orElse(null)
                )*/
                .idzirot(dto.getIdzirot())
                .nameot(fio)
                //.naturalIndicators(new ArrayList<>())//только для отдела AXO тут не нужен

                .build();
    }

}
