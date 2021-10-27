package ru.pfr.contracts2.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.contracts2.entity.contracts.MyDocuments;

public interface MyDocumentsRepository extends JpaRepository<MyDocuments, Long> {
}
