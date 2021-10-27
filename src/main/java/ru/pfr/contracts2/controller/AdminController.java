package ru.pfr.contracts2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/contract/admin", "/contract/admin"})
public class AdminController {

    @GetMapping("/")
    public ResponseEntity getTest(){
        try {
            int num = 12;
            String s = String.valueOf(num);
            while (s.length()<3){
                s = "0"+s;
            }
            return ResponseEntity.ok("Пашет! " + s);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }
}
