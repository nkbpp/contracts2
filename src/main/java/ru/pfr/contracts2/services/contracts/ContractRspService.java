package ru.pfr.contracts2.services.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRsp;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRspSpecification;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.ContractRsp_;
import ru.pfr.contracts2.entity.contracts.parent.entity.Notification;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.repositories.contracts.ContractRspRepository;
import ru.pfr.contracts2.services.log.LogiService;
import ru.pfr.contracts2.services.mail.MailSender;
import ru.pfr.contracts2.services.zir.ZirServise;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
@Transactional
public class ContractRspService {

    private final MailSender mailSender;
    private final ZirServise zirServise;
    final ContractRspRepository contractRspRepository;
    private final LogiService logiService;
    public static final int SIZE = 30;

    public ContractRsp findById(Long id) {
        return contractRspRepository.findById(id).orElse(null);
    }

    public List<ContractRsp> findAll() {
        return contractRspRepository.findAll(Sort.by(ContractRsp_.ID).descending());
    }

    public List<ContractRsp> findAll(int page) {
        //для обрезки
        PageRequest pageRequest = PageRequest.of(page, SIZE, Sort.by(ContractRsp_.ID).descending());
        return contractRspRepository.findAll(pageRequest).getContent();
    }

    public List<ContractRsp> findAll(Specification<ContractRsp> specification) {
        return contractRspRepository.findAll(specification);
    }

    public List<ContractRsp> findAll(Specification<ContractRsp> specification, Pageable pageable) {
        return contractRspRepository.findAll(specification, pageable).getContent();
    }

    public void save(ContractRsp contract) {
        //рассчитываем расчетную дату
        LocalDateTime date = contract.getDate_ispolnenija_GK();
        if (date != null) {
            contract.setRaschet_date(date.plusDays(2));
        }
        contractRspRepository.save(contract);
    }

    public void delete(Long id) {
        contractRspRepository.deleteById(id);
    }

    public int getColSize() {
        List<ContractRsp> contracts = findAll();
        return contracts.size();
    }

    public int getColIspolneno() {
        List<ContractRsp> contracts = findAll(ContractRspSpecification.ispolnenoIs(true));
        return contracts.size();
    }

    public int getColNotispolneno() {
        List<ContractRsp> contracts = findAll(ContractRspSpecification.ispolnenoIs(false));
        return contracts.size();
    }

    public List<ContractRsp> getNotispolnenosrok() {
        return findAll(ContractRspSpecification.ispolnenoIs(false))
                .stream()
                .filter(contract ->
                        contract.getDaysOst() <= 4 &&
                                contract.getDaysOst() >= 0 &&
                                contract.getRaschet_date() != null
                ).toList();
    }

    public int getColNotispolnenosrok() {
        return getNotispolnenosrok().size();
    }

    public List<ContractRsp> getNodate() {
        return findAll(ContractRspSpecification.ispolnenoIs(false))
                .stream()
                .filter(contract ->
                        contract.getDaysOst() < 0 &&
                                contract.getRaschet_date() == null
                ).toList();
    }

    public int getColNodate() {
        return getNodate().size();
    }

    public List<ContractRsp> getProsrocheno() {
        return findAll(ContractRspSpecification.ispolnenoIs(false))
                .stream()
                .filter(contract ->
                        contract.getDaysOst() < 0 &&
                                contract.getRaschet_date() != null
                ).toList();
    }

    public int getColProsrocheno() {
        return getProsrocheno().size();
    }

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    //@Scheduled(cron = "0 20 16 * * MON-FRI")
    //@Scheduled(fixedRate = 60000)
    @Async
    @Transactional
    public void sendEmails() {
        List<ContractRsp> contracts = this.findAll(
                ContractRspSpecification.ispolnenoEquals(false)
        );

        contracts.forEach(contract -> {

            long days = contract.getDaysOst();

            //System.out.println("Дней " + days);
            if (days >= 0 && days <= 4) {

                String subject = "\"Предупреждение! Возврат обеспечения исполнения контракта!\"!";
                String text = "Осталось дней: " + days +
                        "\n Наименование контрагента: " + contract.getKontragentRsp().getName() +
                        "\n ИНН контрагента: " + contract.getKontragentRsp().getInn() +
                        "\n Номер контракта: " + contract.getNomGK() +
                        "\n Дата контракта: " + (contract.getDateGK() == null ? " Дата контракта не введена!" : DateTimeFormatter.ofPattern("dd.MM.yyyy").format(contract.getDateGK().toLocalDate())) +
                        "\n Вид обеспечения: " + contract.getVidObespRsp().getName() +
                        "\n Сумма: " + contract.getSumOk();

                try {
                    //письмо создателю
                    String emailUser = null;
                    //сообщение создателю
                    try {
                        emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                        mailSender.send(zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir()))), subject, text);
                    } catch (Exception e) {
                        logiService.save(new Logi("this", "MailError", "Ошибка при отправке сообщения! Не удалось отправить письмо создателю!"));
                    }
                    //письмо создателю
                    //String emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                    //mailSender.send(zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir()))), subject, text); //сообщение создателю
                    String emailBoss = null;
                    try {
                        emailBoss = zirServise.getEmailBossById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));

                        //System.out.println("Босс " + emailBoss);
                        if (emailBoss != null && (emailUser == null || !emailUser.equals(emailBoss))) {
                            mailSender.send(emailBoss, subject, text); //сообщение начальнику отдела
                        }
                    } finally { //если с боссом возникли проблемы все равно отправить уведомления
                        for (Notification notification : contract.getNotifications()) {
                            String email = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(notification.getId_user())));
                            //System.out.println("Кто то " + email);
                            if ((emailUser == null || !emailUser.equals(email)) && (emailBoss != null && !emailBoss.equals(email))) {
                                mailSender.send(email, subject, text);//сообщение остальным
                            }
                        }
                    }
                /*String emailUser = zirServise.getEmailUserById(1997);
                System.out.println("Создатель " + emailUser);
                mailSender.send(
                        zirServise.getEmailUserById(1997),
                        subject,text); //сообщение мне*/
                } catch (Exception e) {
                    logiService.save(new Logi("this", "MailError", "Ошибка при отправке сообщения!"));
                }
            }
        });
    }

}
