package ru.pfr.contracts2.service.contracts;

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
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDkSpecification;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk_;
import ru.pfr.contracts2.entity.contracts.parent.entity.Notification;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.repository.contracts.ContractDkRepository;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.mail.MailSender;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
//@EnableScheduling
@Transactional
public class ContractDkService {
    private final MailSender mailSender;
    private final ZirServise zirServise;
    final ContractDkRepository contractDkRepository;
    private final LogiService logiService;
    public static final int SIZE = 30;

    public ContractDk findById(Long id) {
        return contractDkRepository.findById(id).orElse(null);
    }

    public List<ContractDk> findAll() {
        return contractDkRepository.findAll(Sort.by(ContractDk_.ID).descending());
    }

    public List<ContractDk> findAll(int page) {
        //для обрезки
        PageRequest pageRequest = PageRequest.of(page, SIZE, Sort.by(ContractDk_.ID).descending());
        return contractDkRepository.findAll(pageRequest).getContent();
    }

    public List<ContractDk> findAll(Specification<ContractDk> specification) {
        return contractDkRepository.findAll(specification);
    }

    public List<ContractDk> findAll(Specification<ContractDk> specification, Pageable pageable) {
        return contractDkRepository.findAll(specification, pageable).getContent();
    }

    public void save(ContractDk contract) {
        //рассчитываем расчетную дату
        LocalDateTime date = contract.getDate_ispolnenija_GK();
        if (date != null) {
            contract.setRaschet_date(date.plusDays(2));
        }
        contractDkRepository.save(contract);
    }

    public void delete(Long id) {
        contractDkRepository.deleteById(id);
    }

    public int getColSize() {
        List<ContractDk> contracts = findAll();
        return contracts.size();
    }

    public int getColIspolneno() {
        List<ContractDk> contracts = findAll(ContractDkSpecification.ispolnenoIs(true));
        return contracts.size();
    }

    public int getColNotispolneno() {
        List<ContractDk> contracts = findAll(ContractDkSpecification.ispolnenoIs(false));
        return contracts.size();
    }

    public List<ContractDk> getNotispolnenosrok() {
        return findAll(ContractDkSpecification.ispolnenoIs(false))
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

    public List<ContractDk> getNodate() {
        return findAll(ContractDkSpecification.ispolnenoIs(false))
                .stream()
                .filter(contract ->
                        contract.getDaysOst() < 0 &&
                                contract.getRaschet_date() == null
                ).toList();
    }

    public int getColNodate() {
        return getNodate().size();
    }

    public List<ContractDk> getProsrocheno() {
        return findAll(ContractDkSpecification.ispolnenoIs(false))
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
        List<ContractDk> contracts = this.findAll(
                ContractDkSpecification.ispolnenoEquals(false)
        );

        contracts.forEach(contract -> {

            long days = contract.getDaysOst();

            //System.out.println("Дней " + days);
            if (days >= 0 && days <= 4) {

                String subject = "\"Предупреждение! Возврат обеспечения исполнения контракта!\"!";
                String text = "Осталось дней: " + days +
                        "\n Наименование контрагента: " + contract.getKontragent().getName() +
                        "\n ИНН контрагента: " + contract.getKontragent().getInn() +
                        "\n Номер контракта: " + contract.getNomGK() +
                        "\n Дата контракта: " + (contract.getDateGK() == null ? " Дата контракта не введена!" : DateTimeFormatter.ofPattern("dd.MM.yyyy").format(contract.getDateGK().toLocalDate())) +
                        "\n Вид обеспечения: " + contract.getVidObesp().getName() +
                        "\n Сумма: " + contract.getSumOk();

                try {
                    //письмо создателю
                    String emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                    //System.out.println("Создатель " + emailUser);

                    mailSender.send(zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir()))), subject, text); //сообщение создателю
                    String emailBoss = null;
                    try {
                        emailBoss = zirServise.getEmailBossById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));

                        //System.out.println("Босс " + emailBoss);
                        if (emailBoss != null && !emailUser.equals(emailBoss)) {
                            mailSender.send(emailBoss, subject, text); //сообщение начальнику отдела
                        }
                    } finally { //если с боссом возникли проблемы все равно отправить уведомления
                        for (Notification notification : contract.getNotifications()) {
                            String email = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(notification.getId_user())));
                            //System.out.println("Кто то " + email);
                            if (!emailUser.equals(email) && (emailBoss != null && !emailBoss.equals(email))) {
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
