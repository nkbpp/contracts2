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
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/kontragent"})
public class KontragentControllerRest {

    private final KontragentService kontragentService;

    @PostMapping("/add")
    public ResponseEntity add(
            @RequestParam String name,
            @RequestParam String inn,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            kontragentService.save(new Kontragent(name, inn));
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", "Данные добавлены!");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/delette")
    public ResponseEntity delette(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            kontragentService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
