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
import ru.pfr.contracts2.entity.contractIT.dto.ContractDopRequest;
import ru.pfr.contracts2.entity.contractIT.dto.FilterContractIt;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractIT.entity.ContractITSpecification;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT_;
import ru.pfr.contracts2.entity.contractIT.entity.ItDocuments;
import ru.pfr.contracts2.entity.contractIT.mapper.ContractItMapper;
import ru.pfr.contracts2.entity.contractIT.mapper.ItDocumentsMapper;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.GetOtdel;
import ru.pfr.contracts2.service.it.ContractItService;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/dop"})
public class ContractDopControllerRest {

    private final ContractItService contractItService;
    private final ItDocumentsMapper documentsMapper;
    private final ContractItMapper contractItMapper;

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
            @RequestPart("contract") ContractDopRequest contractDopRequest,

            @AuthenticationPrincipal User user,
            Authentication authentication) {
        try {

            //проход по документам
            List<ItDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractIT contract = contractItMapper
                    .fromContractDopRequest(contractDopRequest);
            contract.setRole(GetOtdel.get(authentication));
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
    @PutMapping(
            value = "",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            }
    )
    public ResponseEntity<?> update(
            @RequestPart("file") List<MultipartFile> documents,
            @RequestPart("contract") ContractDopRequest contractDopRequest,

            @AuthenticationPrincipal User user,
            Authentication authentication) {
        try {

            //проход по документам
            List<ItDocuments> listDocuments = documents
                    .stream()
                    .map(documentsMapper::fromMultipart)
                    .filter(Objects::nonNull)
                    .toList();

            ContractIT oldContract = contractItService.findById(contractDopRequest.getId());

            ContractIT contract = contractItMapper
                    .fromContractDopRequest(contractDopRequest);
            contract.setRole(GetOtdel.get(authentication));

            contract.setAllDocuments(oldContract.getItDocuments());
            contract.setAllDocuments(listDocuments);

            contract.setDate_create(oldContract.getDate_create());

            contract.setUser(user);

            contractItService.update(contract);
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
                    contractItMapper.toDto(contractItService.findById(id)),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }

    /**
     * Фильтр
     */
    @PostMapping(value = "/findTable")
    public ResponseEntity<?> findTable(
            @RequestBody FilterContractIt filterContractIt,

            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            Authentication authentication
    ) {

        try {
            Sort sort = Sort.by(ContractIT_.ID).descending();
            if (filterContractIt.sortd() == 1) {
                sort = Sort.by(ContractIT_.DATE_GK).and(Sort.by(ContractIT_.ID).descending());
            } else if (filterContractIt.sortd() == 2) {
                sort = Sort.by(ContractIT_.DATE_GK).descending().and(Sort.by(ContractIT_.ID).descending());
            }
            if (filterContractIt.sortk() == 1) {
                sort = Sort.by(ContractIT_.KONTRAGENT).and(Sort.by(ContractIT_.ID).descending());
            } else if (filterContractIt.sortk() == 2) {
                sort = Sort.by(ContractIT_.KONTRAGENT).descending().and(Sort.by(ContractIT_.ID).descending());
            }

            List<ContractIT> contracts = contractItService.findAll(
                    ContractITSpecification.filterContractIt(
                            filterContractIt, GetOtdel.get(authentication)
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

}
