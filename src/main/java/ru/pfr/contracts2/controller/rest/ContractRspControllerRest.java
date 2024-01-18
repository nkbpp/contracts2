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
import ru.pfr.contracts2.aop.log.valid.ValidError;
import ru.pfr.contracts2.entity.contracts.contractRsp.dto.ContractRspDto;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRspSpecification;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRsp_;
import ru.pfr.contracts2.entity.contracts.contractRsp.mapper.ContractRspMapper;
import ru.pfr.contracts2.entity.contracts.parent.dto.StatDto;
import ru.pfr.contracts2.entity.contracts.parent.entity.MyDocuments;
import ru.pfr.contracts2.entity.contracts.parent.mapper.MyDocumentsMapper;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.services.contracts.ContractRspService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp"})
public class ContractRspControllerRest {

    private final MyDocumentsMapper myDocumentsMapper;
    private final ContractRspMapper contractRspMapper;
    private final ContractRspService contractRspService;

    /**
     * Добавить контракт
     */
    @ValidError
    @PostMapping(
            value = "/contract",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> add(
            @RequestPart("file") List<MultipartFile> documents,
            @Valid @RequestPart("contract") ContractRspDto contractDto,
            @AuthenticationPrincipal User user,
            Errors errors
    ) {
        try {
            ContractRsp cont = contractRspMapper.fromDto(contractDto);

            //проход по документам
            List<MyDocuments> listDocuments = documents
                    .stream()
                    .map(myDocumentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractRsp newContract = new ContractRsp(
                    null,
                    cont.getReceipt_date(), cont.getPlat_post(),
                    cont.getNomGK(), cont.getDateGK(),
                    cont.getPredmet_contract(),
                    cont.getSum(),
                    cont.getDate_ispolnenija_GK(),
                    cont.getCol_days(),
                    cont.getNotifications(),
                    cont.getIspolneno(),
                    listDocuments,
                    cont.getNomerZajavkiNaVozvrat(),
                    cont.getDateZajavkiNaVozvrat(),
                    user,
                    cont.getKontragentRsp(),
                    cont.getVidObespRsp()
            );

            contractRspService.save(newContract);
            return ResponseEntity.ok("Данные добавлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Изменить контракт
     */
    @ValidError
    @PutMapping(
            value = "/contract",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> upload(
            @RequestPart("file") List<MultipartFile> documents,
            @Valid @RequestPart("contract") ContractRspDto contractDto,
            Errors errors
    ) {

        try {
            ContractRsp cont = contractRspMapper.fromDto(contractDto);

            //проход по документам
            List<MyDocuments> listDocuments = documents
                    .stream()
                    .map(myDocumentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractRsp newContract = contractRspService.findById(cont.getId());
            newContract.setPlat_post(cont.getPlat_post());
            newContract.setReceipt_date(cont.getReceipt_date());
            newContract.setKontragentRsp(cont.getKontragentRsp());
            newContract.setNomGK(cont.getNomGK());
            newContract.setDateGK(cont.getDateGK());
            newContract.setPredmet_contract(cont.getPredmet_contract());
            newContract.setVidObespRsp(cont.getVidObespRsp());
            newContract.setSum(cont.getSum());

            newContract.setDate_ispolnenija_GK(cont.getDate_ispolnenija_GK());
            newContract.setCol_days(cont.getCol_days());

            newContract.setIspolneno(cont.getIspolneno());
            newContract.setNomerZajavkiNaVozvrat(cont.getNomerZajavkiNaVozvrat());
            newContract.setDateZajavkiNaVozvrat(cont.getDateZajavkiNaVozvrat());

            newContract.setAllNotification(cont.getNotifications());

            newContract.setAllDocuments(listDocuments);

            contractRspService.save(newContract);
            return ResponseEntity.ok("Данные обновлены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении!");
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
            contractRspService.delete(id);
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
            ContractRsp contract = contractRspService.findById(id);
            return new ResponseEntity<>(contractRspMapper.toDto(contract), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /***
     * @return список людей для уведомления
     */
/*    @PostMapping("/dopnotification")
    public ResponseEntity<?> dopnotification() {
        try {

            List<String> notifications = zirServise.getAllIdUserByIdOtdel("147");

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
    }*/
    @PostMapping("/viewBadge")
    public ResponseEntity<?> viewBadge() {
        try {
            return ResponseEntity.ok(contractRspService.getColNotispolnenosrok());
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
            List<ContractRsp> contracts = contractRspService.findAll(
                    ContractRspSpecification.filterContract(
                            poleFindByNomGK, poleFindByINN,
                            poleFindByIspolneno, poleFindByNotIspolneno
                    ),
                    PageRequest.of(
                            page == 0 ? 0 : page - 1,
                            ContractRspService.SIZE,
                            Sort.by(ContractRsp_.ID).descending()
                    )
            );

            return new ResponseEntity<>(
                    contracts.stream()
                            .map(contractRspMapper::toDto)
                            .toList(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Просрочено контрактов
     */
    @GetMapping(path = "/statProsrocheno")
    public ResponseEntity<?> statProsrocheno() {
        try {
            return new ResponseEntity<>(
                    contractRspService.getProsrocheno()
                            .stream()
                            .map(contractRspMapper::toDto)
                            .toList(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Не задан срок исполнения контракта
     */
    @GetMapping(path = "/statNodate")
    public ResponseEntity<?> statNodate() {
        try {
            return new ResponseEntity<>(
                    contractRspService.getNodate()
                            .stream()
                            .map(contractRspMapper::toDto)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Приближается срок исполнения контрактов
     */
    @GetMapping(path = "/statNotIspolnenoSrok")
    public ResponseEntity<?> statNotIspolnenoSrok() {
        try {
            return new ResponseEntity<>(
                    contractRspService.getNotispolnenosrok()
                            .stream()
                            .map(contractRspMapper::toDto)
                            .toList(),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /***
     * Статистика
     */
    @GetMapping("/stat")
    public ResponseEntity<?> stat() {
        try {
            return new ResponseEntity<>(
                    new StatDto(
                            contractRspService.getColSize(),
                            contractRspService.getColIspolneno(),
                            contractRspService.getColNotispolneno(),
                            contractRspService.getColNotispolnenosrok(),
                            contractRspService.getColNodate(),
                            contractRspService.getColProsrocheno()
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            ContractRsp contract = contractRspService.findById(id);
            contract.setIspolneno(!contract.getIspolneno());
            contractRspService.save(contract);
            return ResponseEntity.ok("Отметка добавлена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при изменении отметки об исполнении ID = " + id + "!");
        }
    }

}
