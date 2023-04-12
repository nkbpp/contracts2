package ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.DopDocumentsDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;

@Component
@RequiredArgsConstructor
public class DopDocumentsMapper {

    public DopDocumentsDto toDto(DopDocuments obj) {
        return DopDocumentsDto.builder()
                .id(obj.getId())
                .nameFile(obj.getNameFile())
                .build();
    }

    public DopDocuments fromDto(DopDocumentsDto dto) {
        return DopDocuments.builder()
                .id(dto.getId())
                .nameFile(dto.getNameFile())
                .build();
    }

    public DopDocuments fromMultipart(MultipartFile file) {
        String nameFile = file.getOriginalFilename();
        if (!file.isEmpty() && nameFile != null && !nameFile.equals("")) {
            try {
                byte[] bytes = file.getBytes();
                return new DopDocuments(bytes, nameFile);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
