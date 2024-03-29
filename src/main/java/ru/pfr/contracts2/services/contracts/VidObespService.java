package ru.pfr.contracts2.services.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.VidObesp;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.VidObesp_;
import ru.pfr.contracts2.repositories.contracts.VidObespRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
@Transactional
public class VidObespService {

    final VidObespRepository vidObespRepository;

    public List<VidObesp> findAll() {
        return vidObespRepository.findAll(Sort.by(VidObesp_.ID).descending());
    }

    public List<VidObesp> findAllwithPusto() {
        List<VidObesp> vidObesps = new ArrayList<>();
        vidObesps.add(new VidObesp(0L, ""));
        vidObesps.addAll(findAll());
        return vidObesps;
    }

    public VidObesp findById(Long id) {
        return vidObespRepository.findById(id).orElse(null);
    }

    public void save(VidObesp vidObesp) {
        vidObespRepository.save(vidObesp);
    }

    public void delete(Long id) {
        vidObespRepository.deleteById(id);
    }

}
