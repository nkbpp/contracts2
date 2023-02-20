package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contractIT.ItDocuments;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.it.ItDocumentsService;
import ru.pfr.contracts2.service.log.LogiService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/dop", "/contract/axo"})
public class DocumentDopControllerRest {
    private final ItDocumentsService itDocumentsService;
    private final LogiService logiService;

    @GetMapping("/download")
    public @ResponseBody
    ResponseEntity<?> download(
            @RequestParam Long id,
            @AuthenticationPrincipal User user
    ) {
        ItDocuments itDocuments = itDocumentsService.findById(id);
        logiService.save(new Logi(user.getLogin(), "Скачивание документа id = " + id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + Translit.cyr2lat(itDocuments.getNameFile()) + "\"")
                .body(itDocuments.getDokument());
    }

    /**
     * Удалить документ
     */
    @DeleteMapping(value = {"/delItDoc/{id}", "/delAxoDoc/{id}"})
    public ResponseEntity<?> delItDoc(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user
    ) {
        try {
            String myDocumentsName = itDocumentsService.findById(id).getNameFile();
            itDocumentsService.delete(id);
            logiService.save(new Logi(user.getLogin(), "Del",
                    "Удаление документа с id = " + id));
            return ResponseEntity.ok(
                    "Документ с именем " + myDocumentsName + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "Ошибка при удалении файла с ID = " + id + " !");
        }
    }

}
