package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contractIT.ItDocuments;
import ru.pfr.contracts2.entity.contractIT.NaturalIndicator;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.it.ItDocumentsService;
import ru.pfr.contracts2.service.it.NaturalIndicatorService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/it"})
public class ContractItControllerRest {

    private final ContractItService contractItService;

    private final ItDocumentsService itDocumentsService;
    private final NaturalIndicatorService naturalIndicatorService;//todo

    private final LogiService logiService;

    @PostMapping("/upload")
    public ResponseEntity upload(
            @RequestParam(defaultValue = "undefined") String id,
            @RequestParam String nomGK,
            @RequestParam String kontragent,
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
            @RequestParam List<MultipartFile> itDocuments,

            @RequestParam(defaultValue = "-1") Double sumNaturalIndicators,
            @RequestParam(defaultValue = "") String naturalIndicators,

            @RequestParam(defaultValue = "") String doc,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model) {
        try {

            //String role = User.getRole(authentication);
            String role = "IT";

            //???????????? ???? ????????????????????
            List<ItDocuments> listDocuments = new ArrayList<>();
            for (MultipartFile file :
                    itDocuments) {
                String nameFile = file.getOriginalFilename();
                if (!file.isEmpty() && !nameFile.equals("")) {
                    try {
                        byte[] bytes = file.getBytes();
                        ItDocuments myDocuments1 = new ItDocuments(bytes, nameFile);
                        listDocuments.add(myDocuments1);

                        System.out.println("???? ???????????? ?????????????????? " + nameFile);
                    } catch (Exception e) {
                        ResponseEntity.badRequest().body(
                                "?????? ???? ?????????????? ?????????????????? " + nameFile + " => " + e.getMessage());
                    }
                } else {
                    ResponseEntity.badRequest().body(
                            "?????? ???? ?????????????? ?????????????????? " + nameFile + " ???????????? ?????? ???????? ????????????.");
                }
            }

            ContractIT contract;

            Date dateGK2;
            dateGK2 = ConverterDate.stringToDate(dateGK.trim());

            //???????????? ???? ?????????????????????? ??????????????????????
            List<NaturalIndicator> naturalIndicators1 = new ArrayList<>();
            List<Double> naturalIndicatorsDoubles =
                    naturalIndicators.length()>0?
                            List.of(naturalIndicators.split(";")).stream()
                    .mapToDouble(d -> Double.parseDouble(d))
                    .boxed()
                    .toList() : new ArrayList<>();

            for (Double d:
                    naturalIndicatorsDoubles) {
                NaturalIndicator naturalIndicator = new NaturalIndicator(d);
                naturalIndicators1.add(naturalIndicator);
            }

            if(id.equals("undefined")){ // ????????????????????
                if(sumNaturalIndicators==-1){ //???? ?????????????????? ???????? ???????????? ???? ??????????????????
                    sumNaturalIndicators=0D;
                }
                contract = new ContractIT(
                        nomGK.trim(), kontragent.trim(),  dateGK2, sum,
                        January, February, March, April, May, June,
                        July, August, September, October, November, December,
                        sumNaturalIndicators, naturalIndicators1,
                        doc.trim(), listDocuments, user, role);
                logiService.save(new Logi(user.getLogin(),"Add",
                        "???????????????????? it ??????????????????"));
            } else { // ??????????????????
                contract=contractItService.findById(Long.valueOf(id));
                contract.setNomGK(nomGK.trim());
                contract.setKontragent(kontragent.trim());
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

                if(sumNaturalIndicators!=-1){ //???? ?????????????????? ???????? ???????????? ???? ??????????????????
                    contract.setSumNaturalIndicators(sumNaturalIndicators);
                    contract.setAllNaturalIndicators(naturalIndicators1);
                }

                contract.setDocumentu(doc.trim());
                contract.setAllDocuments(listDocuments);
                logiService.save(new Logi(user.getLogin(),"Upd",
                        "?????????????????? ?????????????????? ?? id = " + id));
            }
            contractItService.save(contract);
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
        ItDocuments itDocuments = itDocumentsService.findById(id);
        logiService.save(new Logi(user.getLogin(),"???????????????????? ?????????????????? id = " + id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + Translit.cyr2lat(itDocuments.getNameFile()) + "\"")
                .body(itDocuments.getDokument());
    }

    @PostMapping("/delItDoc")
    public ResponseEntity delItDoc(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            String myDocumentsName = itDocumentsService.findById(id).getNameFile();
            itDocumentsService.delete(id);
            logiService.save(new Logi(user.getLogin(),"Del",
                    "???????????????? ?????????????????? ?? id = " + id));
            return ResponseEntity.ok(
                    "???????????????? ?? ???????????? " + myDocumentsName + " ?????????????? ????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "???????????? ?????? ???????????????? ?????????? ?? ID = " + id + " !");
        }
    }

    @PostMapping("/deleteContract")
    public ResponseEntity deleteContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            contractItService.delete(id);
            logiService.save(new Logi(user.getLogin(),"Del",
                    "???????????????? ?????????????????? ?? id = " + id));
            return ResponseEntity.ok("???????????????? ?? ID = " + id + " ?????????????? ????????????!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
        }
    }

    @PostMapping("/getContract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            ContractIT contract = contractItService.findById(id);

            Map<String, String> map = new HashMap<>();//TODO ????????????????????????????????
            map.put("id", contract.getId().toString());
            map.put("nomGK", contract.getNomGK());
            map.put("kontragent", contract.getKontragent());
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

            map.put("sumNaturalIndicators", contract.getSumNaturalIndicatorsStr());
            map.put("naturalIndicatorsSize",
                    String.valueOf(contract.getNaturalIndicators().size()));

            String s = contract.getNaturalIndicatorsStr();
            map.put("naturalIndicators", contract.getNaturalIndicatorsStr());

            map.put("doc", contract.getDocumentu());

            Map<String, Object> map2 = new HashMap<>();
            map2.put("documents",contract.getItDocuments());
            map2.put("contract",map);

            return new ResponseEntity<>(map2, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("????????????!");
        }
    }

}
