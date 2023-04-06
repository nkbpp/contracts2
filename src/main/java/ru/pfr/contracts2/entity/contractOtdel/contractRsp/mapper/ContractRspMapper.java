package ru.pfr.contracts2.entity.contractOtdel.contractRsp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.DopDocumentsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.MonthsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractItRosRequest;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.contractOtdel.contractRsp.dto.ContractRspDto;
import ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.service.it.ContractRspService;
import ru.pfr.contracts2.service.zir.ZirServise;

import javax.validation.Valid;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ContractRspMapper {

    private final DopDocumentsMapper documentsMapper;
    /*private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;
    private final BudgetClassificationRepository budgetClassificationRepository;*/

    private final ZirServise zirServise;

    private final ContractRspService contractRspService;
    private final MonthsMapper monthsMapper;

    public ContractRspDto toDto(ContractRsp obj) {
        return ContractRspDto.builder()
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
                /*.budgetClassification(
                        obj.getBudgetClassification() == null ?
                                null :
                                budgetClassificationResponseMapper.apply(obj.getBudgetClassification())
                )*/
                .build();
    }

    public ContractRsp fromDto(@Valid ContractItRosRequest dto) {

        String fio = "";
        try {
            fio = zirServise.getNameUserById(dto.getIdzirot());
        } catch (Exception e) {
        }

        if (dto.getId() != null) {
            ContractRsp contractRsp = contractRspService.findById(dto.getId());
            contractRsp.setNomGK(dto.getNomGK());
            contractRsp.setKontragent(dto.getKontragent());
            contractRsp.setDateGK(dto.getDateGK());
            contractRsp.setDateGKs(dto.getDateGKs());
            contractRsp.setDateGKpo(dto.getDateGKpo());
            contractRsp.setStatusGK(StatusGk.customValueOf(dto.getStatusGK()));
            contractRsp.setSum(dto.getSum());
            contractRsp.setMonths(monthsMapper.fromDto(dto.getMonths()));
            contractRsp.setIdzirot(dto.getIdzirot());
            contractRsp.setNameot(fio);
            return contractRsp;
        }

        return ContractRsp.builder()

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
                //.naturalIndicators(new ArrayList<>())//только для отдела AXO тут не нужен

                .build();
    }

}
