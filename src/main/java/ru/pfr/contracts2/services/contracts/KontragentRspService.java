package ru.pfr.contracts2.services.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp_;
import ru.pfr.contracts2.repositories.contracts.KontragentRspRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
@Transactional
public class KontragentRspService {

    final KontragentRspRepository kontragentRepository;

    public List<KontragentRsp> findAll() {
        return kontragentRepository.findAll(Sort.by(KontragentRsp_.ID).descending());
    }

    public List<KontragentRsp> findAll(int page) {
        return kontragentRepository.findAll(PageRequest.of(page, 12)).getContent();
    }

    public List<KontragentRsp> findByNameAndInn(String name, String inn) {
        if ((name == null || name.equals("")) && (inn == null || inn.equals(""))) {
            return findAll();
        }
        return kontragentRepository.findAll(
                Specification.where(
                        (root, query, criteriaBuilder) ->
                                criteriaBuilder.and(
                                        criteriaBuilder.like(
                                                root.get(KontragentRsp_.NAME), "%" + name + "%"
                                        ),
                                        criteriaBuilder.like(
                                                root.get(KontragentRsp_.INN), "%" + inn + "%"
                                        )
                                )
                )
        );
    }

    public List<KontragentRsp> findAllwithPusto() {
        List<KontragentRsp> kontragents = new ArrayList<>();
        kontragents.add(new KontragentRsp(0L, "", ""));
        kontragents.addAll(findAll());
        return kontragents;
    }

    public KontragentRsp findById(Long id) {
        return kontragentRepository.findById(id).orElse(null);
    }

    public void save(KontragentRsp kontragent) {
        kontragentRepository.save(kontragent);
    }

    public void delete(Long id) {
        kontragentRepository.deleteById(id);
    }

}
