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
import ru.pfr.contracts2.entity.contractIT.mapper.ContractAxoMapper;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.global.Translit;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.it.ItDocumentsService;
import ru.pfr.contracts2.service.it.NaturalIndicatorService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/axo"})
public class ContractAxoControllerRest {

    private final ContractItService contractItService;

    private final ItDocumentsService itDocumentsService;
    private final NaturalIndicatorService naturalIndicatorService;//todo


    private final LogiService logiService;

    private final ContractAxoMapper contractAxoMapper;

    @PostMapping("/upload")
    public ResponseEntity upload(
            @RequestParam(defaultValue = "undefined") String id,
            @RequestParam String nomGK,
            @RequestParam String kontragent,
            @RequestParam String dateGK,
            @RequestParam(defaultValue = "") String dateGKs,
            @RequestParam(defaultValue = "") String dateGKpo,
            @RequestParam(defaultValue = "") String statusGK,
            @RequestParam(defaultValue = "0") Double sum,
            @RequestParam(defaultValue = "0") Integer idzirot,
            @RequestParam(defaultValue = "0") Long budgetClassificationId,

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
            String role = "AXO";

            //проход по документам
            List<ItDocuments> listDocuments = new ArrayList<>();
            for (MultipartFile file :
                    itDocuments) {
                String nameFile = file.getOriginalFilename();
                if (!file.isEmpty() && !nameFile.equals("")) {
                    try {
                        byte[] bytes = file.getBytes();
                        ItDocuments myDocuments1 = new ItDocuments(bytes, nameFile);
                        listDocuments.add(myDocuments1);

                        System.out.println("Вы удачно сохранили " + nameFile);
                    } catch (Exception e) {
                        ResponseEntity.badRequest().body(
                                "Вам не удалось загрузить " + nameFile + " => " + e.getMessage());
                    }
                } else {
                    ResponseEntity.badRequest().body(
                            "Вам не удалось загрузить " + nameFile + " потому что файл пустой.");
                }
            }

            ContractIT contract;

            Date dateGK2;
            dateGK2 = ConverterDate.stringToDate(dateGK.trim());
            Date dateGKs2;
            dateGKs2 = ConverterDate.stringToDate(dateGKs.trim());
            Date dateGKpo2;
            dateGKpo2 = ConverterDate.stringToDate(dateGKpo.trim());

            //проход по натуральным показателям
            List<NaturalIndicator> naturalIndicators1 = new ArrayList<>();
            List<Double> naturalIndicatorsDoubles =
                    naturalIndicators.length() > 0 ?
                            List.of(naturalIndicators.split(";")).stream()
                                    .mapToDouble(d -> Double.parseDouble(d))
                                    .boxed()
                                    .toList() : new ArrayList<>();

            for (Double d :
                    naturalIndicatorsDoubles) {
                NaturalIndicator naturalIndicator = new NaturalIndicator(d);
                naturalIndicators1.add(naturalIndicator);
            }


            if (id.equals("undefined")) { // Добавление
                if (sumNaturalIndicators == -1) { //не обновлять если ничего не приходило
                    sumNaturalIndicators = 0D;
                }
                contract = new ContractIT(null,
                        nomGK.trim(), kontragent.trim(), statusGK,
                        dateGK2, dateGKs2, dateGKpo2, sum,
                        January, February, March, April, May, June,
                        July, August, September, October, November, December,
                        sumNaturalIndicators, naturalIndicators1,
                        doc.trim(), listDocuments, user, role, idzirot, ""/*, null*/);
                logiService.save(new Logi(user.getLogin(), "Add",
                        "Добавление axo контракта"));
            } else { // Изменения
                contract = contractItService.findById(Long.valueOf(id));
                contract.setNomGK(nomGK.trim());
                contract.setKontragent(kontragent.trim());
                contract.setDateGK(dateGK2);
                contract.setDateGKs(dateGKs2);
                contract.setDateGKpo(dateGKpo2);
                contract.setStatusGK(statusGK);
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

                contract.setIdzirot(idzirot);


                if (sumNaturalIndicators != -1) { //не обновлять если ничего не приходило
                    contract.setSumNaturalIndicators(sumNaturalIndicators);
                    contract.setAllNaturalIndicators(naturalIndicators1);
                }

                contract.setDocumentu(doc.trim());
                contract.setAllDocuments(listDocuments);
                logiService.save(new Logi(user.getLogin(), "Upd",
                        "Изменение контракта с id = " + id));
            }
            contractItService.save(contract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @GetMapping("/download")
    public @ResponseBody
    ResponseEntity download(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        ItDocuments itDocuments = itDocumentsService.findById(id);
        logiService.save(new Logi(user.getLogin(), "Скачивание документа id = " + id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + Translit.cyr2lat(itDocuments.getNameFile()) + "\"")
                .body(itDocuments.getDokument());
    }

    @PostMapping("/delAxoDoc")
    public ResponseEntity delAxoDoc(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            String myDocumentsName = itDocumentsService.findById(id).getNameFile();
            itDocumentsService.delete(id);
            logiService.save(new Logi(user.getLogin(), "Del",
                    "Удаление документа с id = " + id));
            return ResponseEntity.ok(
                    "Документ с именем " + myDocumentsName + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    "Ошибка при удалении файла с ID = " + id + " !");
        }
    }

    @PostMapping("/deleteContract")
    public ResponseEntity deleteContract(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {
            contractItService.delete(id);
            logiService.save(new Logi(user.getLogin(), "Del",
                    "Удаление контракта с id = " + id));
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/getContract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model) {
        try {

            return new ResponseEntity<>(contractAxoMapper.toDto(contractItService.findById(id)), HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @GetMapping("/findTable")
    public ResponseEntity<?> findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByKontragent,
            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            Authentication authentication,
            @AuthenticationPrincipal User user,
            Model model) {

        try {
            String role = "AXO";

            List<ContractIT> contracts;
            if (poleFindByNomGK.equals("") && poleFindByKontragent.equals("")) {
                contracts = contractItService.findAllByRole(role);
            } else {
                contracts = contractItService
                        .findByNomGK(poleFindByNomGK, poleFindByKontragent, role);
            }

            return new ResponseEntity<>(
                    contracts == null ? null : contracts
                            .stream()
                            .map(contractAxoMapper::toDto)
                            .collect(Collectors.toList())
                            .stream()
                            .skip((long) col * (param - 1))
                            .limit(col)
                            .collect(Collectors.toList()),
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }


}
