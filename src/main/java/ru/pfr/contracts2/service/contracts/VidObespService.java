package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.repository.contracts.NotificationRepository;
import ru.pfr.contracts2.repository.contracts.VidObespRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
public class VidObespService {

    final VidObespRepository vidObespRepository;

    public List<VidObesp> findAll() {
        return vidObespRepository.findAllByOrderByIdDesc();
    }

    public List<VidObesp> findAllwithPusto() {
        List<VidObesp> vidObesps = new ArrayList<>();
        vidObesps.add(new VidObesp(0L,""));
        vidObesps.addAll(findAll());
        return vidObesps;
    }

    public VidObesp findById(Long id) {
        return vidObespRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(VidObesp vidObesp) {
        vidObespRepository.save(vidObesp);
    }

    @Transactional
    public void delete(Long id) {
        vidObespRepository.deleteById(id);
    }

}
