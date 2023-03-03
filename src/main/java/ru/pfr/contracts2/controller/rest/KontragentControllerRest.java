package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.contracts2.entity.contracts.entity.Kontragent;
import ru.pfr.contracts2.service.contracts.KontragentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/kontragent"})
public class KontragentControllerRest {

    private final KontragentService kontragentService;

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String inn
    ) {
        try {
            Kontragent kontragent = kontragentService.findById(id);
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
            kontragentService.save(new Kontragent(name, inn));
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

}
