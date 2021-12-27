package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/it"})
public class ContractItControllerRest {

    private final ContractItService contractItService;


    private final LogiService logiService;

    @PostMapping("/upload")
    public ResponseEntity upload(
            @RequestParam(defaultValue = "undefined") String id,
            @RequestParam String nomGK,
            @RequestParam String dateGK,
            @RequestParam(defaultValue = "0") Double sum,

            @RequestParam(defaultValue = "0") Double January,
            @RequestParam(defaultValue = "0") Double February,
            @RequestParam(defaultValue = "0") Double March,
            @RequestParam(defaultValue = "0") Double April,
            @RequestParam(defaultValue = "0") Double May,
            @RequestParam(defaultValue = "0") Double June,
            @RequestParam(defaultValue = "0") Double July,
            @RequestParam(defaultValue = "0") Double August,
            @RequestParam(defaultValue = "0") Double September,
            @RequestParam(defaultValue = "0") Double October,
            @RequestParam(defaultValue = "0") Double November,
            @RequestParam(defaultValue = "0") Double December,

            @RequestParam(defaultValue = "") String doc,
            @AuthenticationPrincipal User user,
            Model model) {
        try {

            ContractIT contract;

            Date dateGK2;
            dateGK2 = ConverterDate.stringToDate(dateGK.trim());

            if(id.equals("undefined")){ // Добавление
                contract = new ContractIT(
                        nomGK.trim(), dateGK2, sum,
                        January, February, March, April, May, June,
                        July, August, September, October, November, December,
                        doc.trim(), user);
                logiService.save(new Logi(user.getLogin(),"Add","Добавление it контракта"));
            }else{ // Изменения
                contract=contractItService.findById(Long.valueOf(id));
                contract.setNomGK(nomGK.trim());
                contract.setDateGK(dateGK2);
                contract.setSum(sum);

                contract.setMonth1(January);
                contract.setMonth2(February);
                contract.setMonth3(March);
                contract.setMonth4(April);
                contract.setMonth5(May);
                contract.setMonth6(June);
                contract.setMonth7(July);
                contract.setMonth8(August);
                contract.setMonth9(September);
                contract.setMonth10(October);
                contract.setMonth11(November);
                contract.setMonth12(December);

                contract.setDocumentu(doc.trim());
                logiService.save(new Logi(user.getLogin(),"Upd","Изменение контракта с id = " + id));
            }
            contractItService.save(contract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }


    @PostMapping("/deleteContract")
    public ResponseEntity deleteContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            contractItService.delete(id);
            logiService.save(new Logi(user.getLogin(),"Del","Удаление контракта с id = " + id));
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/getContract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable Long id,
            //@RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            ContractIT contract = contractItService.findById(id);
            Map<String, Object> map2 = new HashMap<>();

            Map<String, String> map = new HashMap<>();//TODO Автоматизировать
            map.put("id", contract.getId().toString());
            map.put("nomGK", contract.getNomGK());
            map.put("dateGK", contract.getDateGKEn());
            map.put("sum", contract.getSumOk());

            map.put("January", contract.getMonth1Ok());
            map.put("February", contract.getMonth2Ok());
            map.put("March", contract.getMonth3Ok());
            map.put("April", contract.getMonth4Ok());
            map.put("May", contract.getMonth5Ok());
            map.put("June", contract.getMonth6Ok());
            map.put("July", contract.getMonth7Ok());
            map.put("August", contract.getMonth8Ok());
            map.put("September", contract.getMonth9Ok());
            map.put("October", contract.getMonth10Ok());
            map.put("November", contract.getMonth11Ok());
            map.put("December", contract.getMonth12Ok());

            map.put("doc", contract.getDocumentu());

            map2.put("contract",map);

            return new ResponseEntity<>(map2, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

}
