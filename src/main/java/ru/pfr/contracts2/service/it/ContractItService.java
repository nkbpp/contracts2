package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;
import ru.pfr.contracts2.repository.it.ContractItRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractItService {

    final ContractItRepository contractItRepository;

    public ContractIT findById(Long id) {
        return contractItRepository.findById(id).orElse(null);
    }

    public List<ContractIT> findAll() {
        return contractItRepository.findAll();
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification) {
        return contractItRepository.findAll(specification);
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification, Pageable pageable) {
        return contractItRepository.findAll(specification, pageable).getContent();
    }

    public void save(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    public void update(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    public void delete(Long id) {
        contractItRepository.deleteById(id);
    }

}
