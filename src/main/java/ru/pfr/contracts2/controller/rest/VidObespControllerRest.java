package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.VidObesp;
import ru.pfr.contracts2.services.contracts.VidObespService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/vidobesp"})
public class VidObespControllerRest {

    private final VidObespService vidObespService;

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestParam String name
    ) {
        try {
            vidObespService.save(new VidObesp(name));
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при добавлении!");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestParam String name
    ) {
        try {
            VidObesp vidObesp = vidObespService.findById(id);
            String old = vidObesp.getName();
            vidObesp.setName(name);
            vidObespService.save(vidObesp);
            return ResponseEntity.ok("Данные изменены c " + old + " на " + vidObesp.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении!");
        }
    }

    @PostMapping("/delette")
    public ResponseEntity<?> delete(
            @RequestParam Long id
    ) {
        try {
            vidObespService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Не удалось удалить запись с ID = " + id + ". Возможно есть контракт с этим видом обеспечения");
        }
    }

    /***
     * получить все
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(
                    vidObespService.findAll(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
