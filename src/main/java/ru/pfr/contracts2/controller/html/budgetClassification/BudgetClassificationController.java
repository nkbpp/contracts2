package ru.pfr.contracts2.controller.html.budgetClassification;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.contractIT.dto.BudgetClassificationDto;
import ru.pfr.contracts2.entity.contractIT.entity.BudgetClassificationResponseMapper;
import ru.pfr.contracts2.global.GetOtdel;
import ru.pfr.contracts2.service.budgetClassification.BudgetClassificationService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/budgetClassification"})
public class BudgetClassificationController {

    private final BudgetClassificationService budgetClassificationService;

    private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;

    @ModelAttribute(name = "budgetClassifications")
    public List<BudgetClassificationDto> budgetClassification(
            Authentication authentication
    ) {
        return budgetClassificationService.findByRole(GetOtdel.get(authentication))
                .stream()
                .map(budgetClassificationResponseMapper)
                .toList();
    }

    @GetMapping(value = {"/budgetClassificationViev"})
    public String budgetClassificationViev() {
        return "fragment/budgetClassification::budgetClassification";
    }

}
