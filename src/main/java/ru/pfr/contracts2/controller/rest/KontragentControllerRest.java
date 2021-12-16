package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.contracts2.entity.contracts.Kontragent;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/kontragent"})
public class KontragentControllerRest {

    private final KontragentService kontragentService;
    private final LogiService logiService;

    @PostMapping("/update")
    public ResponseEntity update(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String inn,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            Kontragent kontragent = kontragentService.findById(id);
            String oldName = kontragent.getName();
            String oldInn = kontragent.getInn();
            kontragent.setName(name);
            kontragent.setInn(inn);
            kontragentService.save(kontragent);

            logiService.save(new Logi(user.getLogin(),"Upd","Изменение контрагента с id = " + id));
            return ResponseEntity.ok("Данные изменены c " + oldName + " на " + kontragent.getName() +
                    " c " + oldInn + " на " + kontragent.getInn());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении!");
        }
    }

    @PostMapping("/add")
    public ResponseEntity add(
            @RequestParam String name,
            @RequestParam String inn,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            kontragentService.save(new Kontragent(name, inn));
            logiService.save(new Logi(user.getLogin(),"Add","Добавление контрагента"));
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при добавлении!");
        }
    }

    @PostMapping("/delette")
    public ResponseEntity delette(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            kontragentService.delete(id);
            logiService.save(new Logi(user.getLogin(),"Del","Удаление контрагента с id = " + id));
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось удалить запись с ID = " + id + "!");
        }
    }

}
