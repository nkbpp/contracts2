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
@RequestMapping(value = {"/contract/main"})
public class ContractControllerRest {

    private final VidObespService vidObespService;
    private final ContractService contractService;
    private final UserService userService;
    private final MyDocumentsService myDocumentsService;

    @PostMapping("/upload")
    public ResponseEntity upload(
            @RequestParam String id,
            @RequestParam String receipt_date,
            @RequestParam String name_koltr,
            @RequestParam String nomGK,
            @RequestParam String dateGK,
            @RequestParam String predmet_contract,
            @RequestParam Long vidObesp,
            @RequestParam Float sum,
            @RequestParam String date_ispolnenija_GK,
            @RequestParam Integer col_days,
            @RequestParam List<String> notifications,
            @RequestParam List<MultipartFile> myDocuments,
            @RequestParam Boolean ispolneno,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            VidObesp vidObesp1 = vidObespService.findById(vidObesp);

            //проход по документам
            List<MyDocuments> listDocuments = new ArrayList<>();
            for (MultipartFile file :
                    myDocuments) {
                String nameFile = file.getOriginalFilename();
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        MyDocuments myDocuments1 = new MyDocuments(bytes, nameFile);
                        myDocumentsService.save(myDocuments1);//!!!!!!!!!!!!!!!!!!!!!!
                        listDocuments.add(myDocuments1);

                        System.out.println("Вы удачно сохранили " + nameFile);
                    } catch (Exception e) {
                        ResponseEntity.badRequest().body("Вам не удалось загрузить " + nameFile + " => " + e.getMessage());
                    }
                } else {
                    ResponseEntity.badRequest().body("Вам не удалось загрузить " + nameFile + " потому что файл пустой.");
                }
            }
            Contract contract;

            Date receipt_date2 = ConverterDate.stringToDate(receipt_date);
            Date dateGK2 = ConverterDate.stringToDate(dateGK);
            Date date_ispolnenija_GK2 = ConverterDate.stringToDate(date_ispolnenija_GK);

            if(id.equals("undefined")){ // Добавление
                contract = new Contract(receipt_date2, name_koltr,
                        nomGK, dateGK2, predmet_contract, vidObesp1, sum,
                        date_ispolnenija_GK2, col_days,
                        null, ispolneno, listDocuments, user);
            }else{ // Изменения
                contract=contractService.findById(Long.valueOf(id));
                contract.setReceipt_date(receipt_date2);
                contract.setName_koltr(name_koltr);
                contract.setNomGK(nomGK);
                contract.setDateGK(dateGK2);
                contract.setPredmet_contract(predmet_contract);
                contract.setVidObesp(vidObesp1);
                contract.setSum(sum);
                contract.setDate_ispolnenija_GK(date_ispolnenija_GK2);
                contract.setCol_days(col_days);
                //contract.setNotifications(null);
                contract.setIspolneno(ispolneno);
                List<MyDocuments> myDocuments1 = contract.getMyDocuments();
                myDocuments1.addAll(listDocuments);
                contract.setMyDocuments(myDocuments1);
            }
            contractService.save(contract);//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", "Данные добавлены!");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @GetMapping("/download")
    public @ResponseBody
    ResponseEntity downloadxml(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        MyDocuments myDocuments = myDocumentsService.findById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Translit.cyr2lat(myDocuments.getNameFile()) + "\"")
                .body(myDocuments.getDokument());
    }

    @PostMapping("/deleteContract")
    public ResponseEntity deleteContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            contractService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/getContract")
    public ResponseEntity<?> getContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            Contract contract = contractService.findById(id);
            Map<String, Object> map2 = new HashMap<>();

            Map<String, String> map = new HashMap<>();
            map.put("id", contract.getId().toString());
            map.put("receipt_date", contract.getReceipt_dateEn());
            map.put("name_koltr", contract.getName_koltr());
            map.put("nomGK", contract.getNomGK());
            map.put("dateGK", contract.getDateGKEn());
            map.put("predmet_contract", contract.getPredmet_contract());
            map.put("vidObesp", contract.getVidObesp() != null ? contract.getVidObesp().getId().toString() : "");
            map.put("sum", contract.getSum().toString());
            map.put("date_ispolnenija_GK", contract.getDate_ispolnenija_GKEn());
            map.put("col_days", contract.getCol_days().toString());
            //map.put("raschet_date", contract.getRaschet_dateRu());
            //map.put("notifications", contract.getNotifications());
            map.put("ispolneno", contract.getIspolneno().toString());
            //map.put("myDocuments", contract.getMyDocuments());

            map2.put("contract",map);
            List<MyDocuments> myDocuments = contract.getMyDocuments();
            for (MyDocuments d:
                 myDocuments) {
                d.setDokument(null);
            }
            map2.put("documents",contract.getMyDocuments());
            return new ResponseEntity<>(map2, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/setIspolneno")
    public ResponseEntity setIspolneno(
            @RequestParam Long id,
            @RequestParam Boolean ispolneno,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            Contract contract = contractService.findById(id);
            contract.setIspolneno(ispolneno);
            contractService.save(contract);
            HashMap<String, Object> map = new HashMap<>();
            map.put("text", "Отметка добавлена!");
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/delDoc")
    public ResponseEntity delDoc(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            myDocumentsService.delete(id);
            return ResponseEntity.ok("Данные удалены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
