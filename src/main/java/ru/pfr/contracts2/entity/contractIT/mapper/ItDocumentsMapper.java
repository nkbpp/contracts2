package ru.pfr.contracts2.entity.contractIT.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
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

    public ItDocuments fromMultipart(MultipartFile file) {
        String nameFile = file.getOriginalFilename();
        if (!file.isEmpty() && !nameFile.equals("")) {
            try {
                byte[] bytes = file.getBytes();
                return new ItDocuments(bytes, nameFile);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
