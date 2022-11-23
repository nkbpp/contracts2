package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.ItDocuments;
import ru.pfr.contracts2.entity.contractIT.dto.ItDocumentsDto;

@Component
@RequiredArgsConstructor
public class ItDocumentsMapper {

    public ItDocumentsDto toDto(ItDocuments obj) {
        return ItDocumentsDto.builder()
                .id(obj.getId())
                .nameFile(obj.getNameFile())
                .build();
    }

    public ItDocuments fromDto(ItDocumentsDto dto) {
        return ItDocuments.builder()
                .id(dto.getId())
                .nameFile(dto.getNameFile())
                .build();
    }

}
