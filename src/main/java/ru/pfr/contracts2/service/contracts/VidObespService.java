package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.repository.contracts.NotificationRepository;
import ru.pfr.contracts2.repository.contracts.VidObespRepository;

import java.util.List;


@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
public class VidObespService {

    final VidObespRepository vidObespRepository;

    public List<VidObesp> findAll() {
        return vidObespRepository.findAll();
    }

    public VidObesp findById(Long id) {
        return vidObespRepository.findById(id).orElse(null);
    }

}
