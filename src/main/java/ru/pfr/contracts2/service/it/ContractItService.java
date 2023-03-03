package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractIT.entity.ContractIT;
import ru.pfr.contracts2.repository.it.ContractItRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional
public class ContractItService {

    final ContractItRepository contractItRepository;

    private final int COL = 30;

    public ContractIT findById(Long id) {
        return contractItRepository.findById(id).orElse(null);
    }

    private List<ContractIT> findAll() {
        return contractItRepository.findAllByOrderByIdDesc();
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification) {
        return contractItRepository.findAll(specification);
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification, Pageable pageable) {
        return contractItRepository.findAll(specification, pageable).getContent();
    }

    public List<ContractIT> findAllByRole(String role) {
        return contractItRepository.findAllByRole(role);
    }

    public List<ContractIT> findByIds(List<Integer> list) {
        return contractItRepository.findByIds(list);
    }

    public List<ContractIT> findAll(String role) {
        return contractItRepository.findAllByRoleOrderByIdDesc(role);
    }

    public List<ContractIT> findAll(String role, int page) {
        return contractItRepository.findAllByRoleOrderByIdDesc(role, PageRequest.of(page, COL));
    }

    public List<ContractIT> findAllcut(int page) {
        return contractItRepository.findAll(PageRequest.of(page, COL)).getContent();
    }

    public List<ContractIT> findAllcut(int page, String role) {
        return contractItRepository.findAllByRoleOrderByIdDesc(role, PageRequest.of(page, COL));
    }

    //поиск по номеру гк и контрагенту
    public List<ContractIT> findByNomGK(String nomgk, String kontragent, String role) {
        return contractItRepository.findByNomGKAndKontragent(nomgk, kontragent, role);
    }

    public void save(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    public void update(ContractIT contractIT) {
        contractIT.setDate_update(LocalDateTime.now());
        contractItRepository.save(contractIT);
    }

    public void delete(Long id) {
        contractItRepository.deleteById(id);
    }

    public int getCOL() {
        return COL;
    }

}
