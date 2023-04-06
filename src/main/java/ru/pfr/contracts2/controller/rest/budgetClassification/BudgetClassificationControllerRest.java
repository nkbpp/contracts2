package ru.pfr.contracts2.controller.rest.budgetClassification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.dto.BudgetClassificationDto;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.BudgetClassification;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.BudgetClassificationRequestMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.BudgetClassificationResponseMapper;
import ru.pfr.contracts2.global.GetOtdel;
import ru.pfr.contracts2.service.budgetClassification.BudgetClassificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contract/main/budgetClassification")
public class BudgetClassificationControllerRest {

    private final BudgetClassificationService budgetClassificationService;
    private final BudgetClassificationResponseMapper responseMapper;
    private final BudgetClassificationRequestMapper requestMapper;

    /**
     * Удалить +
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable("id") Long id
    ) {
        try {
            budgetClassificationService.delete(id);
            return new ResponseEntity<>("Удаление прошло успешно!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Найти ID
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(
            @PathVariable("id") Long id
    ) {
        try {
            return new ResponseEntity<>(budgetClassificationService.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обновить +
     */
    @PutMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(
            @RequestBody BudgetClassificationDto budgetClassificationDto
    ) {
        try {
            budgetClassificationService.update(requestMapper.apply(budgetClassificationDto).get());
            return new ResponseEntity<>("Изменено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Добавление +
     */
    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> add(
            @RequestBody BudgetClassificationDto budgetClassificationDto,
            Authentication authentication
    ) {
        try {
            BudgetClassification budgetClassification = requestMapper.apply(budgetClassificationDto).get();
            budgetClassification.setRole(GetOtdel.get(authentication));
            budgetClassificationService.save(budgetClassification);
            return new ResponseEntity<>("Добавлено", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Получить все +?
     */
    @PostMapping(path = "/All")
    public ResponseEntity<List<BudgetClassificationDto>> getAll(
            @RequestParam(defaultValue = "30") Integer col,
            @RequestParam(defaultValue = "1") Integer pagination,
            Authentication authentication
    ) {
        try {
            return new ResponseEntity<>(
                    budgetClassificationService.findAll(
                                    Specification.where(
                                            (root, query, criteriaBuilder) ->
                                                    criteriaBuilder.equal(
                                                            root.get("role"), GetOtdel.get(authentication)
                                                    )
                                    ),
                                    PageRequest.of(
                                            pagination,
                                            col,
                                            Sort.by("id").descending()
                                    )
                            )
                            .stream()
                            .map(responseMapper)
                            .toList(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
