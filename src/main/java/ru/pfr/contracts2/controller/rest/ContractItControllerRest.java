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
import org.webjars.NotFoundException;
import ru.pfr.contracts2.aop.log.valid.ValidError;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.ContractItRosRequest;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.FilterContractItRsp;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatisticDto;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.DopDocuments;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.DopDocumentsMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractITSpecification;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT_;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.mapper.ContractItMapper;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.it.ContractItService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/it"})
public class ContractItControllerRest {

    private final ContractItService contractItService;
    private final DopDocumentsMapper documentsMapper;
    private final ContractItMapper contractItMapper;

    /**
     * Добавить контракт
     */
    @ValidError
    @PostMapping(
            value = "",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> add(
            @AuthenticationPrincipal User user,
            @RequestPart("file") List<MultipartFile> documents,
            @Valid @RequestPart("contract") ContractItRosRequest contractItRosRequest,
            Errors errors) {
        try {

            //проход по документам
            List<DopDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractIT contract = contractItMapper
                    .fromDto(contractItRosRequest);
            contract.setAllDocuments(listDocuments);
            contract.setUser(user);

            contractItService.save(contract);
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
            value = "",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> update(
            @AuthenticationPrincipal User user,
            @RequestPart("file") List<MultipartFile> documents,
            @Valid @RequestPart("contract") ContractItRosRequest contractItRosRequest,
            Errors errors) {
        try {

            //проход по документам
            List<DopDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractIT dto = contractItMapper.fromDto(contractItRosRequest);

            ContractIT contractIT = contractItService.findById(dto.getId()).orElseThrow(
                    () -> new NotFoundException("Contract with id = " + dto.getId() + " not found")
            );
            contractIT.setNomGK(dto.getNomGK());
            contractIT.setKontragent(dto.getKontragent());
            contractIT.setDateGK(dto.getDateGK());
            contractIT.setDateGKs(dto.getDateGKs());
            contractIT.setDateGKpo(dto.getDateGKpo());
            contractIT.setStatusGK(dto.getStatusGK());
            contractIT.setSum(dto.getSum());
            contractIT.setMonths(dto.getMonths());
            contractIT.setIdzirot(dto.getIdzirot());
            contractIT.setNameot(dto.getNameot());
            contractIT.setDayNotification(dto.getDayNotification());

            /*ContractIT contract = contractItMapper
                    .fromDto(contractItRosRequest);*/

            contractIT.setAllDocuments(listDocuments);
            contractIT.setUser(user);

            contractItService.update(contractIT);
            return ResponseEntity.ok("Данные изменены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Удалить контракт по id +?
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(
            @PathVariable("id") Long id
    ) {
        try {
            contractItService.delete(id);
            return ResponseEntity.ok("Контракт с ID = " + id + " успешно удален!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Получить контракт по id
     */
    @GetMapping("/getContract/{id}")
    public ResponseEntity<?> getContract(
            @PathVariable Long id
    ) {
        try {
            return new ResponseEntity<>(
                    contractItMapper.toDto(contractItService.findById(id).orElseThrow(
                            () -> new NotFoundException("Contract with id = " + id + " not found")
                            )
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Фильтр
     */
    @ValidError
    @PostMapping(value = "/findTable")
    public ResponseEntity<?> findTable(
            @Valid @RequestBody FilterContractItRsp filterContractItRsp,

            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            Errors errors
    ) {

        try {
            Sort sort = Sort.by(ContractIT_.ID).descending();
            if (filterContractItRsp.getSortd() == 1) {
                sort = Sort.by(ContractIT_.DATE_GK).and(Sort.by(ContractIT_.ID).descending());
            } else if (filterContractItRsp.getSortd() == 2) {
                sort = Sort.by(ContractIT_.DATE_GK).descending().and(Sort.by(ContractIT_.ID).descending());
            }
            if (filterContractItRsp.getSortk() == 1) {
                sort = Sort.by(ContractIT_.KONTRAGENT).and(Sort.by(ContractIT_.ID).descending());
            } else if (filterContractItRsp.getSortk() == 2) {
                sort = Sort.by(ContractIT_.KONTRAGENT).descending().and(Sort.by(ContractIT_.ID).descending());
            }

            List<ContractIT> contracts = contractItService.findAll(
                    ContractITSpecification.filterContract(
                            filterContractItRsp//, GetOtdel.get(authentication)
                    ),
                    PageRequest.of(
                            param - 1, col,
                            sort
                    )
            );

            return new ResponseEntity<>(
                    (contracts == null) ? null : contracts
                            .stream()
                            .map(contractItMapper::toDto)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /***
     * Статистика
     */
    @GetMapping("/stat")
    public ResponseEntity<?> stat() {
        try {
            return new ResponseEntity<>(
                    new StatisticDto(
                            contractItService.findAll().size(),
                            contractItService.findAll(
                                    ContractITSpecification.statusGKEquals(
                                            StatusGk.EXECUTED
                                    )
                            ).size(),
                            contractItService.findAll(
                                    ContractITSpecification.statusGKEquals(
                                            StatusGk.CURRENT
                                    )
                            ).size(),
                            contractItService.findAll(
                                    ContractITSpecification.statusGKEquals(
                                            StatusGk.TERMINATED
                                    )
                            ).size(),
                            contractItService.findAll(
                                    ContractITSpecification.statusGKisEmpty()
                            ).size()
                    ),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
