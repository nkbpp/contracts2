package ru.pfr.contracts2.services.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractOtdel.contractAxo.entity.ContractAxo;
import ru.pfr.contracts2.repositories.it.ContractAxoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractAxoService {

    final ContractAxoRepository contractAxoRepository;

    public Optional<ContractAxo> findById(Long id) {
        return contractAxoRepository.findById(id);
    }

    public List<ContractAxo> findAll(Specification<ContractAxo> specification) {
        return contractAxoRepository.findAll(specification);
    }

    public List<ContractAxo> findAll(Specification<ContractAxo> specification, Pageable pageable) {
        return contractAxoRepository.findAll(specification, pageable).getContent();
    }

    public void save(ContractAxo contractIT) {
        contractAxoRepository.save(contractIT);
    }

    public void update(ContractAxo contractAxo) {
        contractAxoRepository.save(contractAxo);
    }

    public void delete(Long id) {
        contractAxoRepository.deleteById(id);
    }

}
