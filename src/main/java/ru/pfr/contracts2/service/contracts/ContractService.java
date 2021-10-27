package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.repository.contracts.ContractRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContractService {

    final ContractRepository contractRepository;

    private final int COL = 30;

    public Contract findById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findAll(int l) {
        return cutTheList(contractRepository.findAll(), l, COL);
    }

    @Transactional
    public void save(Contract contract) {
        //рассчитываем расчетную дату
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(contract.getDate_ispolnenija_GK());
        calendar.add(Calendar.DATE,contract.getCol_days());
        contract.setRaschet_date(calendar.getTime());

        contract.setDate_update(new Date());

        contractRepository.save(contract);
    }

    @Transactional
    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public int getCOL() {
        return COL;
    }

    //для обрезки
    private List<Contract> cutTheList(List<Contract> contracts, int list, int mnoj) {
        List<Contract> contracts2 = new ArrayList<>();

        int start = mnoj*(list-1);
        int end = mnoj*(list-1) + mnoj;

        for (int i = start; i < end && i<contracts.size() ; i++) {
            contracts2.add(contracts.get(i));
        }

        return contracts2;
    }

}
