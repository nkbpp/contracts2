package ru.pfr.contracts2.entity.contracts.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.dto.VidObespDto;
import ru.pfr.contracts2.entity.contracts.entity.VidObesp;

@Component
@RequiredArgsConstructor
public class VidObespMapper {

    public VidObespDto toDto(VidObesp obj) {
        return VidObespDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .build();
    }

    public VidObesp fromDto(VidObespDto dto) {
        return VidObesp.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
