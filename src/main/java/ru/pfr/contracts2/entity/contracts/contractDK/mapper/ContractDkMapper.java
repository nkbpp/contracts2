package ru.pfr.contracts2.entity.contracts.contractDK.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.contractDK.dto.ContractDkDto;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;
import ru.pfr.contracts2.entity.contracts.parent.mapper.*;
import ru.pfr.contracts2.services.contracts.KontragentService;
import ru.pfr.contracts2.services.contracts.VidObespService;


@Component
@RequiredArgsConstructor
public class ContractDkMapper {

    private final ContractParentMapper contractParentMapper;
    private final KontragentService kontragentService;
    private final KontragentMapper kontragentMapper;
    private final VidObespMapper vidObespMapper;
    private final VidObespService vidObespService;

    public ContractDkDto toDto(ContractDk obj) {
        var parent = contractParentMapper.toDto(obj);

        return ContractDkDto.builder()
                .id(parent.getId())
                .receipt_date(parent.getReceipt_date())//дата поступления
                .plat_post(parent.getPlat_post())//Платежное поручение
                .kontragent(
                        obj.getKontragent() == null ? null :
                                kontragentMapper.toDto(obj.getKontragent()))
                //.kontragent(parent.getKontragent())
                .nomGK(parent.getNomGK())//номер ГК
                .dateGK(parent.getDateGK())//дата ГК
                .predmet_contract(parent.getPredmet_contract())//краткое содержание предмета контракта
                //.vidObesp(parent.getVidObesp())//вид обеспечения
                .vidObesp(obj.getVidObesp() == null ? null :
                        vidObespMapper.toDto(obj.getVidObesp()))//вид обеспечения
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

    public ContractDk fromDto(ContractDkDto dto) {
        var parent = contractParentMapper.fromDto(dto);

        return (
                ContractDk.builder()
                        .id(parent.getId())
                        .receipt_date(parent.getReceipt_date())//дата поступления
                        .plat_post(parent.getPlat_post())//Платежное поручение
                        .kontragent(
                                kontragentService.findById(
                                        kontragentMapper.fromDto(dto.getKontragent()).getId()
                                )
                        )
                        .vidObesp(
                                vidObespService.findById(
                                        vidObespMapper.fromDto(dto.getVidObesp()).getId()
                                )
                        )//вид обеспечения
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
