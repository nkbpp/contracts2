package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractIT.entity.ContractAxo;
import ru.pfr.contracts2.repository.it.ContractAxoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractAxoService {

    final ContractAxoRepository contractAxoRepository;

    public ContractAxo findById(Long id) {
        return contractAxoRepository.findById(id).orElse(null);
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
