package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contractIT.dto.ContractAxoDto;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxoSpecification;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo_;
import ru.pfr.contracts2.entity.contractIT.entity.ItDocuments;
import ru.pfr.contracts2.entity.contractIT.mapper.ContractAxoMapper;
import ru.pfr.contracts2.entity.contractIT.mapper.ItDocumentsMapper;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.it.ContractAxoService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/axo"})
public class ContractAxoControllerRest {

    private final ContractAxoService contractAxoService;
    private final ContractAxoMapper contractAxoMapper;
    private final ItDocumentsMapper documentsMapper;

    /**
     * Добавить контракт
     */
    @PostMapping(
            value = "",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> add(
            @RequestPart("file") List<MultipartFile> documents,
            @RequestPart("contract") ContractAxoDto contractAxoDto,
            @AuthenticationPrincipal User user) {
        try {

            //проход по документам
            List<ItDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractAxo contract = contractAxoMapper
                    .fromDto(contractAxoDto);
            contract.setAllDocuments(listDocuments);
            contract.setUser(user);

            contractAxoService.save(contract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Изменить контракт
     */
    @PutMapping(
            value = "",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> update(
            @RequestPart("file") List<MultipartFile> documents,
            @RequestPart("contract") ContractAxoDto contractAxoDto,
            @AuthenticationPrincipal User user) {
        try {

            //проход по документам
            List<ItDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractAxo oldContract = contractAxoService.findById(contractAxoDto.getId());

            ContractAxo contract = contractAxoMapper
                    .fromDto(contractAxoDto);

            contract.setAllDocuments(oldContract.getItDocuments());
            contract.setAllDocuments(listDocuments);

            contract.setDate_create(oldContract.getDate_create());

            contract.setUser(user);

            contractAxoService.update(contract);
            return ResponseEntity.ok("Данные изменены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

/*    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam(defaultValue = "undefined") String id,
            @RequestParam String nomGK,
            @RequestParam String kontragent,
            @RequestParam String dateGK,
            @RequestParam(defaultValue = "") String dateGKs,
            @RequestParam(defaultValue = "") String dateGKpo,
            @RequestParam(defaultValue = "") String statusGK,
            @RequestParam(defaultValue = "0") Double sum,
            @RequestParam(defaultValue = "0") Integer idzirot,
            //@RequestParam(defaultValue = "0") Long budgetClassificationId,

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
            @AuthenticationPrincipal User user
    ) {
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

            LocalDateTime dateGK2;
            dateGK2 = ConverterDate.stringToLocalDateTime(dateGK.trim());
            LocalDateTime dateGKs2;
            dateGKs2 = ConverterDate.stringToLocalDateTime(dateGKs.trim());
            LocalDateTime dateGKpo2;
            dateGKpo2 = ConverterDate.stringToLocalDateTime(dateGKpo.trim());

            //проход по натуральным показателям
            List<NaturalIndicator> naturalIndicators1 = new ArrayList<>();
            List<Double> naturalIndicatorsDoubles =
                    naturalIndicators.length() > 0 ?
                            Stream.of(naturalIndicators.split(";"))
                                    .mapToDouble(Double::parseDouble)
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
                        doc.trim(), listDocuments, user, role, idzirot, ""*//*, null*//*);

            } else { // Изменения
                contract = contractAxoService.findById(Long.valueOf(id));
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
            }
            contractAxoService.save(contract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }*/

    /**
     * Удалить контракт по id +?
     */
    @DeleteMapping("/deleteContract/{id}")
    public ResponseEntity<?> deleteContract(
            @PathVariable("id") Long id
    ) {
        try {
            contractAxoService.delete(id);
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Получить контракт по id
     */
    @PostMapping("/getContract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable Long id
    ) {
        try {
            return new ResponseEntity<>(
                    contractAxoMapper.toDto(contractAxoService.findById(id)),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Фильтр
     */
    @PostMapping("/findTable")
    public ResponseEntity<?> findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByKontragent,
            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            Authentication authentication
    ) {

        try {

            Sort sort = Sort.by(ContractAxo_.ID).descending();

            List<ContractAxo> contracts = contractAxoService.findAll(
                    ContractAxoSpecification.filterContractAXO(
                            poleFindByNomGK,
                            poleFindByKontragent//,
                            //GetOtdel.get(authentication)
                    ),
                    PageRequest.of(
                            param - 1, col,
                            sort
                    )
            );

            return new ResponseEntity<>(
                    (contracts == null) ? null : contracts
                            .stream()
                            .map(contractAxoMapper::toDto)
                            .toList(),
                    HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }


}
