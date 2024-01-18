package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;
import ru.pfr.contracts2.services.contracts.KontragentRspService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp/kontragent"})
public class KontragentRspControllerRest {

    private final KontragentRspService kontragentService;

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String inn
    ) {
        try {
            KontragentRsp kontragent = kontragentService.findById(id);
            String oldName = kontragent.getName();
            String oldInn = kontragent.getInn();
            kontragent.setName(name);
            kontragent.setInn(inn);
            kontragentService.save(kontragent);

            return ResponseEntity.ok("Данные изменены c " + oldName + " на " + kontragent.getName() +
                    " c " + oldInn + " на " + kontragent.getInn());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении!");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestParam String name,
            @RequestParam String inn
    ) {
        try {
            kontragentService.save(new KontragentRsp(name, inn));
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при добавлении!");
        }
    }

    @PostMapping("/delette")
    public ResponseEntity<?> delette(
            @RequestParam Long id
    ) {
        try {
            kontragentService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось удалить запись с ID = " + id + "!");
        }
    }

    /***
     * получить все
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        try {
            return new ResponseEntity<>(
                    kontragentService.findAll(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
