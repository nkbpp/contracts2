package ru.pfr.contracts2.entity.contracts.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.dto.NotificationDto;
import ru.pfr.contracts2.entity.contracts.entity.Notification;

@Component
@RequiredArgsConstructor
public class NotificationMapper {

    public NotificationDto toDto(Notification obj) {
        return NotificationDto.builder()
                .id(obj.getId())
                .id_user(obj.getId_user())
                .name(obj.getName())
                .build();
    }

    public Notification fromDto(NotificationDto dto) {
        return Notification.builder()
                .id(dto.getId())
                .id_user(dto.getId_user())
                .name(dto.getName())
                .build();
    }
}
