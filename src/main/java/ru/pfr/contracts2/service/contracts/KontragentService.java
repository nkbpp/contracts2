package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.Kontragent;
import ru.pfr.contracts2.repository.contracts.KontragentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
public class KontragentService {

    final KontragentRepository kontragentRepository;

    public List<Kontragent> findAll() {
        return kontragentRepository.findAll();
    }

    public List<Kontragent> findByNameAndInn(String name, String inn) {
        if((name==null || name.equals("")) && (inn==null || inn.equals(""))){
            return kontragentRepository.findAll();
        }
        return kontragentRepository.findByNameAndInn(name, inn);
    }

    public List<Kontragent> findAllwithPusto() {
        List<Kontragent> kontragents = new ArrayList<>();
        kontragents.add(new Kontragent(0L,"",""));
        kontragents.addAll(findAll());
        return kontragents;
    }

    public Kontragent findById(Long id) {
        return kontragentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Kontragent kontragent) {
        kontragentRepository.save(kontragent);
    }

    @Transactional
    public void delete(Long id) {
        kontragentRepository.deleteById(id);
    }
}
