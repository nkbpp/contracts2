package ru.pfr.contracts2.entity.contracts.contractDK.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.contractDK.dto.ContractDkDto;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;
import ru.pfr.contracts2.entity.contracts.parent.mapper.*;

import java.time.LocalDateTime;
import java.time.LocalTime;


@Component
@RequiredArgsConstructor
public class ContractDkMapper {

    private final ContractParentMapper contractParentMapper;

    public ContractDkDto toDto(ContractDk dto) {
        var parent = contractParentMapper.toDto(dto);

        return ContractDkDto.builder()
                .id(parent.getId())
                .receipt_date(parent.getReceipt_date())//дата поступления
                .plat_post(parent.getPlat_post())//Платежное поручение
                .kontragent(parent.getKontragent())
                .nomGK(parent.getNomGK())//номер ГК
                .dateGK(parent.getDateGK())//дата ГК
                .predmet_contract(parent.getPredmet_contract())//краткое содержание предмета контракта
                .vidObesp(parent.getVidObesp())//вид обеспечения
                .sum(parent.getSum())//сумма
                .date_ispolnenija_GK(parent.getDate_ispolnenija_GK())//дата исполнения ГК
                .col_days(parent.getCol_days())//Условия возврата ГК (количество дней от исполнения)
                .raschet_date(parent.getRaschet_date())//Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)
                .notifications(parent.getNotifications())//кого оповестить
                .ispolneno(parent.getIspolneno())//Отметка об исполнении
                .nomerZajavkiNaVozvrat(parent.getNomerZajavkiNaVozvrat())//Номер заявки на возврат
                .dateZajavkiNaVozvrat(parent.getDateZajavkiNaVozvrat())//Номер заявки на возврат
                .myDocuments(parent.getMyDocuments())
                .user(parent.getUser())
                .daysOst(parent.getDaysOst())
                .procent(parent.getProcent())
                .build();

    }

    public ContractDk fromDto(ContractDkDto obj) {
        var parent = contractParentMapper.fromDto(obj);

        return (
                ContractDk.builder()
                        .id(parent.getId())
                        .receipt_date(parent.getReceipt_date())//дата поступления
                        .plat_post(parent.getPlat_post())//Платежное поручение
                        .kontragent(parent.getKontragent())
                        .vidObesp(parent.getVidObesp())//вид обеспечения
                        .nomGK(parent.getNomGK())//номер ГК
                        .dateGK(parent.getDateGK())//дата ГК
                        .predmet_contract(parent.getPredmet_contract())//краткое содержание предмета контракта
                        .sum(parent.getSum())//сумма
                        .date_ispolnenija_GK(parent.getDate_ispolnenija_GK())//дата исполнения ГК
                        .col_days(parent.getCol_days())//Условия возврата ГК (количество дней от исполнения)
                        .notifications(parent.getNotifications())//кого оповестить
                        .ispolneno(parent.getIspolneno())//Отметка об исполнении
                        .nomerZajavkiNaVozvrat(parent.getNomerZajavkiNaVozvrat())//Номер заявки на возврат
                        .dateZajavkiNaVozvrat(parent.getDateZajavkiNaVozvrat())//Номер заявки на возврат
                        .myDocuments(parent.getMyDocuments())
                        .build()
        );
    }

}
