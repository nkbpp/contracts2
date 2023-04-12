package ru.pfr.contracts2.entity.contracts.contractRsp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.pfr.contracts2.entity.contracts.parent.dto.*;
import ru.pfr.contracts2.entity.user.UserDto;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ContractRspDto extends ContractParentDto {
    public ContractRspDto(Long id, LocalDate receipt_date, String plat_post, KontragentDto kontragent, String nomGK, LocalDate dateGK, String predmet_contract, VidObespDto vidObesp, Double sum, LocalDate date_ispolnenija_GK, Integer col_days, LocalDate raschet_date, List<NotificationDto> notifications, Boolean ispolneno, String nomerZajavkiNaVozvrat, LocalDate dateZajavkiNaVozvrat, List<MyDocumentsDto> myDocuments, UserDto user, Long daysOst, Long procent) {
        super(id, receipt_date, plat_post, kontragent, nomGK, dateGK, predmet_contract, vidObesp, sum, date_ispolnenija_GK, col_days, raschet_date, notifications, ispolneno, nomerZajavkiNaVozvrat, dateZajavkiNaVozvrat, myDocuments, user, daysOst, procent);
    }
}
