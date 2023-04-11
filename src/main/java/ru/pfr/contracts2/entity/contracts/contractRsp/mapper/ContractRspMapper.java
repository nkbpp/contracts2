package ru.pfr.contracts2.entity.contracts.contractRsp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.contractRsp.dto.ContractRspDto;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.entity.contracts.parent.mapper.ContractParentMapper;
import ru.pfr.contracts2.service.contracts.KontragentRspService;
import ru.pfr.contracts2.service.contracts.VidObespRspService;


@Component
@RequiredArgsConstructor
public class ContractRspMapper {

    private final ContractParentMapper contractParentMapper;

    private final KontragentRspService kontragentService;
    private final KontragentRspMapper kontragentMapper;
    private final VidObespRspMapper vidObespMapper;
    private final VidObespRspService vidObespService;

    public ContractRspDto toDto(ContractRsp obj) {
        var parent = contractParentMapper.toDto(obj);

        return ContractRspDto.builder()
                .id(parent.getId())
                .receipt_date(parent.getReceipt_date())//дата поступления
                .plat_post(parent.getPlat_post())//Платежное поручение
                .kontragent(
                        obj.getKontragentRsp() == null ? null :
                                kontragentMapper.toDto(obj.getKontragentRsp()))
                .nomGK(parent.getNomGK())//номер ГК
                .dateGK(parent.getDateGK())//дата ГК
                .predmet_contract(parent.getPredmet_contract())//краткое содержание предмета контракта
                .vidObesp(obj.getVidObespRsp() == null ? null :
                        vidObespMapper.toDto(obj.getVidObespRsp()))//вид обеспечения
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

    public ContractRsp fromDto(ContractRspDto dto) {
        var parent = contractParentMapper.fromDto(dto);

        return (
                ContractRsp.builder()
                        .id(parent.getId())
                        .receipt_date(parent.getReceipt_date())//дата поступления
                        .plat_post(parent.getPlat_post())//Платежное поручение
                        .kontragentRsp(
                                kontragentService.findById(
                                        kontragentMapper.fromDto(dto.getKontragent()).getId()
                                )
                        )
                        .vidObespRsp(
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
