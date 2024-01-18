package ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.services.it.ContractItService;
import ru.pfr.contracts2.services.zir.ZirServise;


@Component
@RequiredArgsConstructor
public class ContractDopMapper {

    private final DopDocumentsMapper documentsMapper;

    private final ZirServise zirServise;

    private final ContractItService contractItService;

    private final MonthsMapper monthsMapper;

    /*public ContractDopDto toDto(ContractDop obj) {
        return ContractDopDto.builder()
                .id(obj.getId())
                .nomGK(obj.getNomGK())
                .kontragent(obj.getKontragent())
                .dateGK(obj.getDateGK())
                .sum(obj.getSum())
                .months(monthsMapper.toDto(obj.getMonths()))
                .ostatoc(obj.getSum() -
                        (obj.getMonths().sum()))
                .documents(obj.getDopDocuments() == null ? null : obj.getDopDocuments()
                        .stream()
                        .map(documentsMapper::toDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public ContractDop fromDop(ContractDopRequest dto) {

        String fio = "";
        try {
            fio = zirServise.getNameUserById(dto.getIdzirot());
        } catch (Exception e) {
        }

        if (dto.getId() != null) {
            ContractDop contractDto = contractItService.findById(dto.getId());
            contractDto.setNomGK(dto.getNomGK());
            contractDto.setKontragent(dto.getKontragent());
            contractDto.setDateGK(dto.getDateGK());
            //contractIT.setDateGKs(dto.getDateGKs());
            //contractIT.setDateGKpo(dto.getDateGKpo());
            //contractIT.setStatusGK(dto.getStatusGK());
            contractDto.setSum(dto.getSum());
            contractDto.setMonths(monthsMapper.fromDto(dto.getMonths()));
            //contractIT.setIdzirot(dto.getIdzirot());
            //contractIT.setNameot(fio);
            return contractDto;
        }

        return ContractDop.builder()
                .id(dto.getId())
                .nomGK(dto.getNomGK())
                .kontragent(dto.getKontragent())
                .dateGK(dto.getDateGK())
                //.dateGKs(dto.getDateGKs())
                //.dateGKpo(dto.getDateGKpo())
                //.statusGK(dto.getStatusGK())
                .sum(dto.getSum())
                .months(monthsMapper.fromDto(dto.getMonths()))
                //.idzirot(dto.getIdzirot())
                //.nameot(fio)
                //.naturalIndicators(new ArrayList<>())//только для отдела AXO тут не нужен
                .build();
    }*/

}
