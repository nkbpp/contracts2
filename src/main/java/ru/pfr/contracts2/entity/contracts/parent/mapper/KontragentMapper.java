package ru.pfr.contracts2.entity.contracts.parent.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contracts.parent.dto.KontragentDto;
import ru.pfr.contracts2.entity.contracts.parent.entity.Kontragent;


@Component
@RequiredArgsConstructor
public class KontragentMapper {


    public KontragentDto toDto(Kontragent obj) {
        return KontragentDto.builder()
                .id(obj.getId())
                .inn(obj.getInn())
                .name(obj.getName())
                .nameInn(obj.getInn() + " " + obj.getName())
                .build();
    }

    public Kontragent fromDto(KontragentDto dto) {

        return Kontragent.builder()
                .id(dto.getId())
                .inn(dto.getInn())
                .name(dto.getName())
                .build();
    }
}
