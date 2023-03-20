package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contracts.dto.ContractDto;
import ru.pfr.contracts2.entity.contracts.entity.Contract;
import ru.pfr.contracts2.entity.contracts.entity.Contract_;
import ru.pfr.contracts2.entity.contracts.entity.ContractSpecification;
import ru.pfr.contracts2.entity.contracts.entity.MyDocuments;
import ru.pfr.contracts2.entity.contracts.entity.Notification;
import ru.pfr.contracts2.entity.contracts.mapper.ContractMapper;
import ru.pfr.contracts2.entity.contracts.mapper.MyDocumentsMapper;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class ContractControllerRest {

    private final MyDocumentsMapper myDocumentsMapper;
    private final ContractMapper contractMapper;
    private final ContractService contractService;
    private final ZirServise zirServise;

    /**
     * Добавить контракт
     */
    @PostMapping(
            value = "/contract",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> add(
            @RequestPart("file") List<MultipartFile> documents,
            @RequestPart("contract") ContractDto contractDto,
            @AuthenticationPrincipal User user,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }

        try {
            Contract cont = contractMapper.fromDto(contractDto);

            //проход по документам
            List<MyDocuments> listDocuments = documents
                    .stream()
                    .map(myDocumentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            Contract newContract = new Contract(
                    null,
                    cont.getReceipt_date(), cont.getPlat_post(),
                    cont.getKontragent(),
                    cont.getNomGK(), cont.getDateGK(),
                    cont.getPredmet_contract(),
                    cont.getVidObesp(),
                    cont.getSum(),
                    cont.getDate_ispolnenija_GK(),
                    cont.getCol_days(),
                    cont.getNotifications(),
                    cont.getIspolneno(),
                    listDocuments,
                    cont.getNomerZajavkiNaVozvrat(),
                    cont.getDateZajavkiNaVozvrat(),
                    user
            );

            contractService.save(newContract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Изменить контракт
     */
    @PutMapping(
            value = "/contract",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> upload(
            @RequestPart("file") List<MultipartFile> documents,
            @RequestPart("contract") ContractDto contractDto,
            Errors errors
    ) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }

        try {
            Contract cont = contractMapper.fromDto(contractDto);

            //проход по документам
            List<MyDocuments> listDocuments = documents
                    .stream()
                    .map(myDocumentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            Contract newContract = contractService.findById(cont.getId());
            newContract.setPlat_post(cont.getPlat_post());
            newContract.setReceipt_date(cont.getReceipt_date());
            newContract.setKontragent(cont.getKontragent());
            newContract.setNomGK(cont.getNomGK());
            newContract.setDateGK(cont.getDateGK());
            newContract.setPredmet_contract(cont.getPredmet_contract());
            newContract.setVidObesp(cont.getVidObesp());
            newContract.setSum(cont.getSum());

            newContract.setDate_ispolnenija_GK(cont.getDate_ispolnenija_GK());
            newContract.setCol_days(cont.getCol_days());

            newContract.setIspolneno(cont.getIspolneno());
            newContract.setNomerZajavkiNaVozvrat(cont.getNomerZajavkiNaVozvrat());
            newContract.setDateZajavkiNaVozvrat(cont.getDateZajavkiNaVozvrat());

            newContract.setAllNotification(cont.getNotifications());

            newContract.setAllDocuments(listDocuments);

            contractService.save(newContract);
            return ResponseEntity.ok("Данные обновлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Удалить контракт по id
     */
    @DeleteMapping("/contract/{id}")
    public ResponseEntity<?> deleteContract(
            @PathVariable("id") Long id
    ) {
        try {
            contractService.delete(id);
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * получить контракт
     */
    @GetMapping("/contract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable("id") Long id
    ) {
        try {
            Contract contract = contractService.findById(id);
            return new ResponseEntity<>(contractMapper.toDto(contract), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * изменить отметку о исполнении
     */
    @GetMapping("/setIspolneno/{id}")
    public ResponseEntity<?> setIspolneno(
            @PathVariable("id") Long id
    ) {
        try {
            Contract contract = contractService.findById(id);
            contract.setIspolneno(!contract.getIspolneno());
            contractService.save(contract);
            return ResponseEntity.ok("Отметка добавлена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении отметки об исполнении ID = " + id + "!");
        }
    }

    /***
     * @return список людей для уведомления
     */
    @PostMapping("/dopnotification")
    public ResponseEntity<?> dopnotification() {
        try {

            List<String> notifications = zirServise.getAllIdUserByIdOtdel("147");
                /*List<String> notifications = new ArrayList<>();
                notifications.add("1270");//Нехаева
                notifications.add("1089");//Бессонова
                notifications.add("1681");//Алясина
                notifications.add("3225");//Георгобиани*/

            List<Notification> notifications1 = new ArrayList<>();
            for (String n :
                    notifications) {
                if (!n.equals("undefined") && !n.equals("")) {
                    notifications1.add(
                            new Notification(
                                    Long.valueOf(n),
                                    zirServise.getNameUserById(Integer.parseInt(n))
                            )
                    );
                }
            }

            return new ResponseEntity<>(notifications1, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    @PostMapping("/viewBadge")
    public ResponseEntity<?> viewBadge() {
        try {
            return ResponseEntity.ok(contractService.getColNotispolnenosrok());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }

    }

    /**
     * Получить все
     */
    @PostMapping(path = "/All")
    public ResponseEntity<?> getAll(
            @RequestParam(name = "param", defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByINN,
            @RequestParam(defaultValue = "true") Boolean poleFindByIspolneno,
            @RequestParam(defaultValue = "true") Boolean poleFindByNotIspolneno
    ) {
        try {

            List<Contract> contracts = contractService.findAll(
                    ContractSpecification.filterContract(
                            poleFindByNomGK, poleFindByINN,
                            poleFindByIspolneno, poleFindByNotIspolneno
                    ),
                    PageRequest.of(
                            page == 0 ? 0 : page - 1,
                            ContractService.SIZE,
                            Sort.by(Contract_.ID).descending()
                    )
            );

            return new ResponseEntity<>(
                    contracts.stream()
                            .map(contractMapper::toDto)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
