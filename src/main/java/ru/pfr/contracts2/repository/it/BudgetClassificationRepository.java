package ru.pfr.contracts2.repository.it;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contractIT.BudgetClassification;

import java.util.List;
import java.util.Optional;

public interface BudgetClassificationRepository extends JpaRepository<BudgetClassification, Long> {

    Optional<BudgetClassification> findById(Long id);

    List<BudgetClassification> findByRole(String role);

    void deleteById(Long id);

}
