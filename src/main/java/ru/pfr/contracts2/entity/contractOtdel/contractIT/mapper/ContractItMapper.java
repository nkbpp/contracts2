package ru.pfr.contracts2.entity.contractOtdel.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.DopDocumentsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.MonthsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractItRosRequest;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.dto.ContractITDto;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.zir.ZirServise;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractItMapper {

    private final DopDocumentsMapper documentsMapper;
    /*private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;
    private final BudgetClassificationRepository budgetClassificationRepository;*/

    private final ZirServise zirServise;

    private final ContractItService contractItService;
    private final MonthsMapper monthsMapper;

    public ContractITDto toDto(ContractIT obj) {
        return ContractITDto.builder()
                .id(obj.getId())
                .nomGK(obj.getNomGK())
                .kontragent(obj.getKontragent())
                .dateGK(obj.getDateGK())
                .dateGKs(obj.getDateGKs())
                .dateGKpo(obj.getDateGKpo())
                .sum(obj.getSum())
                .months(monthsMapper.toDto(obj.getMonths()))
                .idzirot(obj.getIdzirot())
                .nameot(obj.getNameot())
                .statusGK(
                        obj.getStatusGK() == null ? null :
                                obj.getStatusGK().getStatus()
                )
                .ostatoc(obj.getSum() - obj.getMonths().sum())
                .documents(obj.getDopDocuments() == null ? null : obj.getDopDocuments()
                        .stream()
                        .map(documentsMapper::toDto)
                        .collect(Collectors.toList())
                )
                .dayNotification(obj.getDayNotification())
                /*.budgetClassification(
                        obj.getBudgetClassification() == null ?
                                null :
                                budgetClassificationResponseMapper.apply(obj.getBudgetClassification())
                )*/
                .build();
    }

    public ContractIT fromDto(@Valid ContractItRosRequest dto) {

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
            contractIT.setStatusGK(StatusGk.customValueOf(dto.getStatusGK()));
            contractIT.setSum(dto.getSum());
            contractIT.setMonths(monthsMapper.fromDto(dto.getMonths()));
            contractIT.setIdzirot(dto.getIdzirot());
            contractIT.setNameot(fio);
            contractIT.setDayNotification(dto.getDayNotification());
            return contractIT;
        }

        return ContractIT.builder()

                .id(dto.getId())
                .nomGK(dto.getNomGK())
                .kontragent(dto.getKontragent())
                .dateGK(dto.getDateGK())
                .dateGKs(dto.getDateGKs())
                .dateGKpo(dto.getDateGKpo())
                .statusGK(StatusGk.customValueOf(dto.getStatusGK()))
                .sum(dto.getSum())
                .months(monthsMapper.fromDto(dto.getMonths()))
                .idzirot(dto.getIdzirot())
                .nameot(fio)
                .dayNotification(dto.getDayNotification())
                //.naturalIndicators(new ArrayList<>())//только для отдела AXO тут не нужен

                .build();
    }

}
