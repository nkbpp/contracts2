package ru.pfr.contracts2.service.budgetClassification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractIT.entity.BudgetClassification;
import ru.pfr.contracts2.repository.budgetClassification.BudgetClassificationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BudgetClassificationService {

    private final BudgetClassificationRepository repository;

    public BudgetClassification findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<BudgetClassification> findAll() {
        return repository.findAll();
    }

    public List<BudgetClassification> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public List<BudgetClassification> findAll(Specification<BudgetClassification> specification, Pageable pageable) {
        return repository.findAll(specification, pageable).getContent();
    }

    public List<BudgetClassification> findByRole(String role) {
        return repository.findByRole(role);
    }

    public void update(BudgetClassification budgetClassification) {
        repository.save(budgetClassification);
    }

    public void save(BudgetClassification budgetClassification) {
        repository.save(budgetClassification);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
