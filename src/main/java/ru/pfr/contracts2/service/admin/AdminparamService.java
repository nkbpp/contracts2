package ru.pfr.contracts2.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.admin.Adminparam;
import ru.pfr.contracts2.repository.admin.AdminparamRepository;

@Service
@RequiredArgsConstructor
public class AdminparamService {

    final AdminparamRepository adminparamRepository;

    @Transactional
    public void save(Adminparam adminparam) {
        adminparamRepository.save(adminparam);
    }

    public Adminparam findByAdminparam() {
        return adminparamRepository.findById(1L).orElse(null);
    }

    public Adminparam findById(Long id) {
        return adminparamRepository.findById(id).orElse(null);
    }

}
