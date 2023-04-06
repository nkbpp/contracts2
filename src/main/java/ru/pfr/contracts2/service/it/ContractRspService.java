package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.entity.contractOtdel.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.repository.it.ContractRspRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractRspService {

    final ContractRspRepository repository;

    public ContractRsp findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ContractRsp> findAll() {
        return repository.findAll();
    }

    public List<ContractRsp> findAll(Specification<ContractRsp> specification) {
        return repository.findAll(specification);
    }

    public List<ContractRsp> findAll(Specification<ContractRsp> specification, Pageable pageable) {
        return repository.findAll(specification, pageable).getContent();
    }

    public void save(ContractRsp contractRsp) {
        repository.save(contractRsp);
    }

    public void update(ContractRsp contractRsp) {
        repository.save(contractRsp);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
