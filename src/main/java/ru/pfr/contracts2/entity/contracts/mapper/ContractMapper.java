package ru.pfr.contracts2.entity.contracts.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.dto.ContractDto;
import ru.pfr.contracts2.entity.contracts.entity.Contract;
import ru.pfr.contracts2.entity.contracts.entity.Notification;
import ru.pfr.contracts2.entity.user.UserMapper;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    private final KontragentMapper kontragentMapper;
    private final KontragentService kontragentService;
    private final VidObespMapper vidObespMapper;
    private final VidObespService vidObespService;
    private final NotificationMapper notificationMapper;
    private final MyDocumentsMapper myDocumentsMapper;
    private final UserMapper userMapper;

    private final ZirServise zirServise;

    public ContractDto toDto(Contract obj) {
        return ContractDto.builder()
                .id(obj.getId())
                .receipt_date(
                        obj.getReceipt_date() == null ? null :
                                obj.getReceipt_date().toLocalDate()
                )//дата поступления
                .plat_post(obj.getPlat_post())//Платежное поручение
                .kontragent(kontragentMapper.toDto(obj.getKontragent()))
                .nomGK(obj.getNomGK())//номер ГК
                .dateGK(
                        obj.getDateGK() == null ? null :
                                obj.getDateGK().toLocalDate()
                )//дата ГК
                .predmet_contract(obj.getPredmet_contract())//краткое содержание предмета контракта
                .vidObesp(vidObespMapper.toDto(obj.getVidObesp()))//вид обеспечения
                .sum(obj.getSum())//сумма
                .date_ispolnenija_GK(
                        obj.getDate_ispolnenija_GK() == null ? null :
                                obj.getDate_ispolnenija_GK().toLocalDate()
                )//дата исполнения ГК
                .col_days(obj.getCol_days())//Условия возврата ГК (количество дней от исполнения)
                .raschet_date(
                        obj.getRaschet_date() == null ? null :
                                obj.getRaschet_date().toLocalDate()
                )//Расчетная дата (дата исполнения ГК + кол дней по условиям возврата + 1 день)
                .notifications(
                        obj.getNotifications().stream()
                                .map(notificationMapper::toDto)
                                .toList()
                )//кого оповестить
                .ispolneno(obj.getIspolneno())//Отметка об исполнении
                .nomerZajavkiNaVozvrat(obj.getNomerZajavkiNaVozvrat())//Номер заявки на возврат
                .dateZajavkiNaVozvrat(
                        obj.getDateZajavkiNaVozvrat() == null ? null :
                                obj.getDateZajavkiNaVozvrat().toLocalDate()
                )//Номер заявки на возврат
                .myDocuments(
                        obj.getMyDocuments().stream()
                                .map(myDocumentsMapper::toDto)
                                .toList()
                )
                .user(userMapper.toDto(obj.getUser()))
                .daysOst(obj.getDaysOst())
                .procent(obj.getProcent())
                .build();
    }

    public Contract fromDto(ContractDto dto) {

        List<Notification> notifications = new ArrayList<>();
        for (var n :
                dto.getNotifications().stream()
                        .map(notificationMapper::fromDto)
                        .toList()
        ) {
            notifications.add(
                    new Notification(
                            n.getId_user(),
                            zirServise.getNameUserById(n.getId_user().intValue())
                    )
            );
        }

        return Contract.builder()
                .id(dto.getId())
                .receipt_date(
                        dto.getReceipt_date() == null ? null :
                                LocalDateTime.of(dto.getReceipt_date(), LocalTime.now())
                )//дата поступления
                .plat_post(dto.getPlat_post())//Платежное поручение
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
                .nomGK(dto.getNomGK())//номер ГК
                .dateGK(
                        dto.getDateGK() == null ? null :
                                LocalDateTime.of(dto.getDateGK(), LocalTime.now())
                )//дата ГК
                .predmet_contract(dto.getPredmet_contract())//краткое содержание предмета контракта
                .sum(dto.getSum())//сумма
                .date_ispolnenija_GK(
                        dto.getDate_ispolnenija_GK() == null ? null :
                                LocalDateTime.of(dto.getDate_ispolnenija_GK(), LocalTime.now())
                )//дата исполнения ГК
                .col_days(dto.getCol_days())//Условия возврата ГК (количество дней от исполнения)
                .notifications(notifications)//кого оповестить
                .ispolneno(dto.getIspolneno())//Отметка об исполнении
                .nomerZajavkiNaVozvrat(dto.getNomerZajavkiNaVozvrat())//Номер заявки на возврат
                .dateZajavkiNaVozvrat(
                        dto.getDateZajavkiNaVozvrat() == null ? null :
                                LocalDateTime.of(dto.getDateZajavkiNaVozvrat(), LocalTime.now())
                )//Номер заявки на возврат
                .myDocuments(
                        dto.getMyDocuments().stream()
                                .map(myDocumentsMapper::fromDto)
                                .toList()
                )
                .build();
    }

}
