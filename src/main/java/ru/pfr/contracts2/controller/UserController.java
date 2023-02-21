package ru.pfr.contracts2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/contract/user"})
public class UserController {

    @GetMapping("/")
    public ResponseEntity<?> registration() {
        try {
            return ResponseEntity.ok("Пашет!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
