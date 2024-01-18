package ru.pfr.contracts2.services.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.VidObespRsp;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.VidObespRsp_;
import ru.pfr.contracts2.repositories.contracts.VidObespRspRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor // создать конструктор для финализируемых полей
@Transactional
public class VidObespRspService {

    final VidObespRspRepository vidObespRepository;

    public List<VidObespRsp> findAll() {
        return vidObespRepository.findAll(Sort.by(VidObespRsp_.ID).descending());
    }

    public List<VidObespRsp> findAllwithPusto() {
        List<VidObespRsp> vidObesps = new ArrayList<>();
        vidObesps.add(new VidObespRsp(0L, ""));
        vidObesps.addAll(findAll());
        return vidObesps;
    }

    public VidObespRsp findById(Long id) {
        return vidObespRepository.findById(id).orElse(null);
    }

    public void save(VidObespRsp vidObesp) {
        vidObespRepository.save(vidObesp);
    }

    public void delete(Long id) {
        vidObespRepository.deleteById(id);
    }

}
