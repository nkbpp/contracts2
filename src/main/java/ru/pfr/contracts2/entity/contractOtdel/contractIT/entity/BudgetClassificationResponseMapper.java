package ru.pfr.contracts2.entity.contractOtdel.contractIT.entity;

import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.dto.BudgetClassificationDto;

import java.util.function.Function;

@Component
public class BudgetClassificationResponseMapper implements Function<BudgetClassification, BudgetClassificationDto> {

    public BudgetClassificationDto apply(BudgetClassification department) {
        return new BudgetClassificationDto(
                department.id,
                department.kod,
                department.name
        );
    }

}
