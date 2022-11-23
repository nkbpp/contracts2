package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.NaturalIndicator;
import ru.pfr.contracts2.entity.contractIT.dto.NaturalIndicatorDto;

@Component
@RequiredArgsConstructor
public class NaturalIndicatorMapper {

    public NaturalIndicatorDto toDto(NaturalIndicator obj) {
        return NaturalIndicatorDto.builder()
                .id(obj.getId())
                .sum(obj.getSum())
                .build();
    }

    public NaturalIndicator fromDto(NaturalIndicatorDto dto) {
        return NaturalIndicator.builder()
                .id(dto.getId())
                .sum(dto.getSum())
                .build();
    }

}
