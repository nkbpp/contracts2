package ru.pfr.contracts2.entity.contracts.contractRsp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.VidObespRsp;
import ru.pfr.contracts2.entity.contracts.parent.dto.VidObespDto;

@Component
@RequiredArgsConstructor
public class VidObespRspMapper {

    public VidObespDto toDto(VidObespRsp obj) {
        return VidObespDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .build();
    }

    public VidObespRsp fromDto(VidObespDto dto) {
        return VidObespRsp.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
