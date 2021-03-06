package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.repository.it.ContractItRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ContractItService {

    final ContractItRepository contractItRepository;

    private final int COL = 30;

    public ContractIT findById(Long id) {
        return contractItRepository.findById(id).orElse(null);
    }

    public List<ContractIT> findAll() {
        return contractItRepository.findAllByOrderByIdDesc();
    }

    public List<ContractIT> findAllByRole(String role) {
        return contractItRepository.findAllByRole(role);
    }

    public List<ContractIT> findByIds(List<Integer> list) {
        return contractItRepository.findByIds(list);
    }

    public List<ContractIT> findAll(int l) {
        return cutTheList(findAll(), l, COL);
    }

    public List<ContractIT> findAll(int l, String role) {
        return cutTheList(contractItRepository.findAllByRoleOrderByIdDesc(role), l, COL);
    }

    //поиск по номеру гк и контрагенту
    public List<ContractIT> findByNomGK(String nomgk, String komtragent, String role) {
        return contractItRepository.findByNomGKAndKontragent(nomgk, komtragent, role);
    }

    @Transactional
    public void save(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    @Transactional
    public void delete(Long id) {
        contractItRepository.deleteById(id);
    }

    public int getCOL() {
        return COL;
    }

    //для обрезки
    private List<ContractIT> cutTheList(List<ContractIT> contracts, int list, int mnoj) {
        List<ContractIT> contracts2 = new ArrayList<>();

        int start = mnoj*(list-1);
        int end = start + mnoj;

        for (int i = start; i < end && i<contracts.size() ; i++) {
            contracts2.add(contracts.get(i));
        }

        return contracts2;
    }

}
