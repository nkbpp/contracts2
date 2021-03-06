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
import ru.pfr.contracts2.entity.contracts.*;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.MyDocumentsService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class ContractControllerRest {

    private final VidObespService vidObespService;
    private final KontragentService kontragentService;
    private final ContractService contractService;
    private final MyDocumentsService myDocumentsService;
    private final ZirServise zirServise;

    private final LogiService logiService;

    @PostMapping("/upload")
    public ResponseEntity upload(
            @RequestParam String id,
            @RequestParam String receipt_date,
            @RequestParam String plat_post,
            @RequestParam Long kontragent,
            @RequestParam String nomGK,
            @RequestParam String dateGK,
            @RequestParam String predmet_contract,
            @RequestParam Long vidObesp,
            @RequestParam Double sum,
            @RequestParam String date_ispolnenija_GK,
            @RequestParam(defaultValue = "0") Integer col_days,
            @RequestParam List<String> notifications,
            @RequestParam List<MultipartFile> myDocuments,
            @RequestParam String nomerZajavkiNaVozvrat,
            @RequestParam String dateZajavkiNaVozvrat,
            @RequestParam Boolean ispolneno,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            VidObesp vidObesp1 = vidObespService.findById(vidObesp);
            Kontragent kontragent1 = kontragentService.findById(kontragent);

            //???????????? ???? ????????????????????
            List<MyDocuments> listDocuments = new ArrayList<>();
            for (MultipartFile file :
                    myDocuments) {
                String nameFile = file.getOriginalFilename();
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        MyDocuments myDocuments1 = new MyDocuments(bytes, nameFile);
                        //myDocumentsService.save(myDocuments1);//!!!!!!!!!!!!!!!!!!!!!!
                        listDocuments.add(myDocuments1);

                        System.out.println("???? ???????????? ?????????????????? " + nameFile);
                    } catch (Exception e) {
                        ResponseEntity.badRequest().body("?????? ???? ?????????????? ?????????????????? " + nameFile + " => " + e.getMessage());
                    }
                } else {
                    ResponseEntity.badRequest().body("?????? ???? ?????????????? ?????????????????? " + nameFile + " ???????????? ?????? ???????? ????????????.");
                }
            }
            List<Notification> notifications1 = new ArrayList<>();
            for (String n:
                 notifications) {
                try{
                    if(!n.equals("undefined") && !n.equals("")){
                        notifications1.add(
                                new Notification(Long.valueOf(n),
                                        zirServise.getNameUserById(Integer.parseInt(n))));
                    }
                }catch (Exception e){
                }
            }

            Contract contract;

            Date receipt_date2 = ConverterDate.stringToDate(receipt_date);
            Date dateGK2 = ConverterDate.stringToDate(dateGK);
            Date dateZajavkiNaVozvrat2 = ConverterDate.stringToDate(dateZajavkiNaVozvrat);
            Date date_ispolnenija_GK2 = ConverterDate.stringToDate(date_ispolnenija_GK);

            if(id.equals("undefined")){ // ????????????????????
                contract = new Contract(receipt_date2, plat_post, kontragent1,
                        nomGK, dateGK2, predmet_contract, vidObesp1, sum,
                        date_ispolnenija_GK2, col_days,
                        notifications1, ispolneno, listDocuments,
                        nomerZajavkiNaVozvrat, dateZajavkiNaVozvrat2, user);
                logiService.save(new Logi(user.getLogin(),"Add","???????????????????? ??????????????????"));
            }else{ // ??????????????????
                contract=contractService.findById(Long.valueOf(id));
                contract.setPlat_post(plat_post);
                contract.setReceipt_date(receipt_date2);
                contract.setKontragent(kontragent1);
                contract.setNomGK(nomGK);
                contract.setDateGK(dateGK2);
                contract.setPredmet_contract(predmet_contract);
                contract.setVidObesp(vidObesp1);
                contract.setSum(sum);

                contract.setDate_ispolnenija_GK(date_ispolnenija_GK2);
                contract.setCol_days(col_days);

                contract.setIspolneno(ispolneno);
                contract.setNomerZajavkiNaVozvrat(nomerZajavkiNaVozvrat);
                contract.setDateZajavkiNaVozvrat(dateZajavkiNaVozvrat2);

                contract.setAllNotification(notifications1);
                contract.setAllDocuments(listDocuments);
                logiService.save(new Logi(user.getLogin(),"Upd","?????????????????? ?????????????????? ?? id = " + id));
            }
            contractService.save(contract);
            return ResponseEntity.ok("???????????? ??????????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
        }
    }

    @GetMapping("/download")
    public @ResponseBody
    ResponseEntity download(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        MyDocuments myDocuments = myDocumentsService.findById(id);
        logiService.save(new Logi(user.getLogin(),"???????????????????? ?????????????????? id = " + id));
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
            logiService.save(new Logi(user.getLogin(),"Del","???????????????? ?????????????????? ?? id = " + id));
            return ResponseEntity.ok("???????????????? ?? ID = " + id + " ?????????????? ????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
        }
    }

    @PostMapping("/getContract")
    public ResponseEntity<?> getContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            Contract contract = contractService.findById(id);


            Map<String, String> map = new HashMap<>();//TODO ????????????????????????????????
            map.put("id", contract.getId().toString());
            map.put("receipt_date", contract.getReceipt_dateEn());
            map.put("plat_post", contract.getPlat_post());
            //map.put("name_koltr", contract.getName_koltr());
            map.put("kontragent", contract.getKontragent() != null ? contract.getKontragent().getId().toString() : "");
            map.put("nomGK", contract.getNomGK());
            map.put("dateGK", contract.getDateGKEn());
            map.put("predmet_contract", contract.getPredmet_contract());
            map.put("vidObesp", contract.getVidObesp() != null ? contract.getVidObesp().getId().toString() : "");
            map.put("sum", contract.getSumOk());
            map.put("date_ispolnenija_GK", contract.getDate_ispolnenija_GKEn());
            map.put("col_days", contract.getCol_days().toString());
            map.put("ispolneno", contract.getIspolneno().toString());
            map.put("nomerZajavkiNaVozvrat", contract.getNomerZajavkiNaVozvrat());
            map.put("dateZajavkiNaVozvrat", contract.getDateZajavkiNaVozvratEn());

            Map<String, Object> map2 = new HashMap<>();
            map2.put("contract",map);
            List<MyDocuments> myDocuments = contract.getMyDocuments();
            for (MyDocuments d:
                 myDocuments) {
                d.setDokument(null);
            }
            map2.put("documents",contract.getMyDocuments());
            map2.put("notifications",contract.getNotifications());
            return new ResponseEntity<>(map2, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
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

            logiService.save(new Logi(user.getLogin(),"?????????????? ?????????????????? ?????????????????????? ?? ???????????????? ispolneno ?? ?????????????????? ?? id = " + id));
            return ResponseEntity.ok("?????????????? ??????????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("???????????? ?????? ?????????????????? ?????????????? ???? ???????????????????? ID = " + id + "!");
        }
    }

    @PostMapping("/delDoc")
    public ResponseEntity delDoc(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            String myDocumentsName = myDocumentsService.findById(id).getNameFile();
            myDocumentsService.delete(id);
            logiService.save(new Logi(user.getLogin(),"Del","???????????????? ?????????????????? ?? id = " + id));
            return ResponseEntity.ok("???????????????? ?? ???????????? " + myDocumentsName + " ?????????????? ????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("???????????? ?????? ???????????????? ?????????? ?? ID = " + id + " !");
        }
    }


    @PostMapping("/dopnotification")
    public ResponseEntity<?> dopnotification(
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            Map<String, Object> map2 = new HashMap<>();
//            if (user.getId_ondel_zir().longValue() == Long.valueOf("148").longValue()) {//147

                List<String> notifications = zirServise.getAllIdUserByIdOtdel("147");
                /*List<String> notifications = new ArrayList<>();
                notifications.add("1270");//??????????????
                notifications.add("1089");//??????????????????
                notifications.add("1681");//??????????????
                notifications.add("3225");//??????????????????????*/

                List<Notification> notifications1 = new ArrayList<>();
                for (String n:
                        notifications) {
                    if(!n.equals("undefined") && !n.equals("")){
                        notifications1.add(new Notification(Long.valueOf(n),zirServise.getNameUserById(Integer.parseInt(n))));
                    }
                }
                map2.put("notifications",notifications1);
/*            } else {
            }*/

            return new ResponseEntity<>(map2, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
        }
    }

    @PostMapping("/viewBadge")
    public ResponseEntity<?> viewBadge(@AuthenticationPrincipal User user,
                            Model model){
        try{
            return ResponseEntity.ok(contractService.getColNotispolnenosrok());
        }catch (Exception e){
            return ResponseEntity.badRequest().body("????????????!");
        }

    }
}
