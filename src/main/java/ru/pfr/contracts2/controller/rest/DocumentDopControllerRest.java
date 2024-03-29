package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.services.it.ItDocumentsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/it", "/contract/axo"})
public class DocumentDopControllerRest {
    private final ItDocumentsService itDocumentsService;

    @GetMapping("/download")
    public @ResponseBody
    ResponseEntity<?> download(
            @RequestParam Long id
    ) {
        DopDocuments dopDocuments = itDocumentsService.findById(id).orElseThrow(
                () -> new NotFoundException("Documents with id = " + id + " not found")
        );
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + Translit.cyr2lat(dopDocuments.getNameFile()) + "\"")
                .body(dopDocuments.getDokument());
    }

    /**
     * Удалить документ
     */
    @DeleteMapping(value = {"/delItDoc/{id}", "/delAxoDoc/{id}"})
    public ResponseEntity<?> delItDoc(
            @PathVariable("id") Long id
    ) {
        try {
            String myDocumentsName = itDocumentsService.findById(id).orElseThrow(
                    () -> new NotFoundException("Documents with id = " + id + " not found")
            ).getNameFile();
            itDocumentsService.delete(id);
            return ResponseEntity.ok(
                    "Документ с именем " + myDocumentsName + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "Ошибка при удалении файла с ID = " + id + " !");
        }
    }

}
