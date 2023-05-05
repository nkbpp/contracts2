package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.Kontragent;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.Kontragent_;
import ru.pfr.contracts2.repository.contracts.KontragentRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
@Transactional
public class KontragentService {

    final KontragentRepository kontragentRepository;

    public List<Kontragent> findAll() {
        return kontragentRepository.findAll(Sort.by(Kontragent_.ID).descending());
    }

    public List<Kontragent> findAll(int page) {
        return kontragentRepository.findAll(PageRequest.of(page, 12)).getContent();
    }

    public List<Kontragent> findByNameAndInn(String name, String inn) {
        if ((name == null || name.equals("")) && (inn == null || inn.equals(""))) {
            return findAll();
        }

        return kontragentRepository.findAll(
                Specification.where(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.and(
                                        criteriaBuilder.like(
                                                root.get(Kontragent_.NAME), "%" + name + "%"
                                        ),
                                        criteriaBuilder.like(
                                                root.get(Kontragent_.INN), "%" + inn + "%"
                                        )
                                )
                )
        );
    }

    public List<Kontragent> findAllwithPusto() {
        List<Kontragent> kontragents = new ArrayList<>();
        kontragents.add(new Kontragent(0L, "", ""));
        kontragents.addAll(findAll());
        return kontragents;
    }

    public Kontragent findById(Long id) {
        return kontragentRepository.findById(id).orElse(null);
    }

    public void save(Kontragent kontragent) {
        kontragentRepository.save(kontragent);
    }

    public void delete(Long id) {
        kontragentRepository.deleteById(id);
    }

}
