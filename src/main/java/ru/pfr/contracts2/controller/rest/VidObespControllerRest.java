package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.MyDocumentsService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.user.UserService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/vidobesp"})
public class VidObespControllerRest {

    private final VidObespService vidObespService;

    @PostMapping("/add")
    public ResponseEntity add(
            @RequestParam String name,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            vidObespService.save(new VidObesp(name));
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
            vidObespService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
