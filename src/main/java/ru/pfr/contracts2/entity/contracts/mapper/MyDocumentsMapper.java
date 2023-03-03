package ru.pfr.contracts2.entity.contracts.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contracts.dto.MyDocumentsDto;
import ru.pfr.contracts2.entity.contracts.entity.MyDocuments;

@Component
@RequiredArgsConstructor
public class MyDocumentsMapper {

    public MyDocumentsDto toDto(MyDocuments obj) {
        return MyDocumentsDto.builder()
                .id(obj.getId())
                .nameFile(obj.getNameFile())
                .build();
    }

    public MyDocuments fromDto(MyDocumentsDto dto) {
        return MyDocuments.builder()
                .id(dto.getId())
                .nameFile(dto.getNameFile())
                .build();
    }

    public MyDocuments fromMultipart(MultipartFile file) {
        String nameFile = file.getOriginalFilename();
        if (!file.isEmpty() && !nameFile.equals("")) {
            try {
                byte[] bytes = file.getBytes();
                return new MyDocuments(bytes, nameFile);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
