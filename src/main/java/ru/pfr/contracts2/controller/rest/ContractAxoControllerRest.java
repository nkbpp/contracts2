package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pfr.contracts2.entity.contractIT.dto.ContractAxoDto;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxoSpecification;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo_;
import ru.pfr.contracts2.entity.contractIT.entity.DopDocuments;
import ru.pfr.contracts2.entity.contractIT.mapper.ContractAxoMapper;
import ru.pfr.contracts2.entity.contractIT.mapper.DopDocumentsMapper;
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
    private final DopDocumentsMapper documentsMapper;

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
            List<DopDocuments> listDocuments = documents
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
            List<DopDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractAxo contract = contractAxoMapper
                    .fromDto(contractAxoDto);
            contract.setAllDocuments(listDocuments);
            contract.setUser(user);

            contractAxoService.update(contract);
            return ResponseEntity.ok("Данные изменены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

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
            @RequestParam(defaultValue = "10") Integer col
    ) {

        try {

            Sort sort = Sort.by(ContractAxo_.ID).descending();

            List<ContractAxo> contracts = contractAxoService.findAll(
                    ContractAxoSpecification.filterContractAXO(
                            poleFindByNomGK,
                            poleFindByKontragent
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
