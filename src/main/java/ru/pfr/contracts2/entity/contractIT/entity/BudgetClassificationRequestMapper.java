package ru.pfr.contracts2.entity.contractIT.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.contracts2.entity.contractIT.dto.BudgetClassificationDto;
import ru.pfr.contracts2.repository.budgetClassification.BudgetClassificationRepository;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class BudgetClassificationRequestMapper implements Function<BudgetClassificationDto, Optional<BudgetClassification>> {

    private final BudgetClassificationRepository repository;

    @Override
    public Optional<BudgetClassification> apply(BudgetClassificationDto request) {
        BudgetClassification department;
        if (request.id() == null) {
            department = new BudgetClassification();
        } else {
            var optionalDepartment = repository.findById(request.id());
            if (optionalDepartment.isEmpty()) {
                return Optional.empty();
            }
            department = optionalDepartment.get();
        }
        department.name = request.name();
        department.kod = request.kod();
        return Optional.of(department);
    }

}
