package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contracts.entity.MyDocuments;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.contracts.MyDocumentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class ContractDocumentControllerRest {
    private final MyDocumentsService myDocumentsService;

    /**
     * Скачать документ по id
     */
    @GetMapping("/download/{id}")
    public @ResponseBody
    ResponseEntity<?> download(
            @PathVariable("id") Long id
    ) {
        MyDocuments myDocuments = myDocumentsService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Translit.cyr2lat(myDocuments.getNameFile()) + "\"")
                .body(myDocuments.getDokument());
    }

    /**
     * Удалить документ по id
     */
    @DeleteMapping("/delDoc/{id}")
    public ResponseEntity<?> delDoc(
            @PathVariable("id") Long id
    ) {
        try {
            String myDocumentsName = myDocumentsService.findById(id).getNameFile();
            myDocumentsService.delete(id);
            return ResponseEntity.ok("Документ с именем " + myDocumentsName + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при удалении файла с ID = " + id + " !");
        }
    }

}
