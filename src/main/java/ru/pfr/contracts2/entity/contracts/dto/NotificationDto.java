package ru.pfr.contracts2.entity.contracts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDto { //уведомление

    private Long id;
    private Long id_user; //id в зире
    private String name; //id в зире

}
