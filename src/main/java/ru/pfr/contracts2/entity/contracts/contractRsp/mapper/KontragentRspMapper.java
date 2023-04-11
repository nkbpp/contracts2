package ru.pfr.contracts2.entity.contracts.contractRsp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;
import ru.pfr.contracts2.entity.contracts.parent.dto.KontragentDto;


@Component
@RequiredArgsConstructor
public class KontragentRspMapper {


    public KontragentDto toDto(KontragentRsp obj) {
        return KontragentDto.builder()
                .id(obj.getId())
                .inn(obj.getInn())
                .name(obj.getName())
                .nameInn(obj.getInn() + " " + obj.getName())
                .build();
    }

    public KontragentRsp fromDto(KontragentDto dto) {

        return KontragentRsp.builder()
                .id(dto.getId())
                .inn(dto.getInn())
                .name(dto.getName())
                .build();
    }
}
