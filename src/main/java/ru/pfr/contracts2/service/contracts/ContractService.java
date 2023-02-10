package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.entity.contracts.Notification;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.repository.contracts.ContractRepository;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.mail.MailSender;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Transactional
public class ContractService {

    private final MailSender mailSender;
    private final ZirServise zirServise;
    final ContractRepository contractRepository;
    private final LogiService logiService;
    private final int SIZE = 30;

    public Contract findById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

/*    public List<Contract> findByfindByNomGK(String nom, String inn) {
        return contractRepository.findByNomGKAndKontragentInn(nom, inn);
    }*/

    public List<Contract> findByfindByNomGK(String name, String inn) {
        return contractRepository.findByNomGKAndKontragentInnScript(name, inn);
    }

    public List<Contract> findAll() {
        return contractRepository.findAllByOrderByIdDesc();
    }

    public List<Contract> findAll(int page) {

        //для обрезки
        PageRequest pageRequest = PageRequest.of(
                page, SIZE,
                Sort
                        .by("id")
                        .descending()
        );
        return contractRepository.findAll(pageRequest).getContent();

    }


    public void save(Contract contract) {
        //рассчитываем расчетную дату
        Date date = contract.getDate_ispolnenija_GK();
        if (date != null) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, (contract.getCol_days() + 2));
            contract.setRaschet_date(calendar.getTime());
        }

        contract.setDate_update(new Date());

        for (MyDocuments d : contract.getMyDocuments()) { //добавляем ссылку на контракт в MyDocuments
            d.setContract(contract);
        }
        for (Notification n : contract.getNotifications()) { //добавляем ссылку на контракт в Notification
            n.setContract(contract);
        }
        contractRepository.save(contract);
    }

    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public int getCOL() {
        return SIZE;
    }

    public List<Contract> findByIspolnenoFalse() {
        return contractRepository.findAllByIspolneno(false);
    }

    public List<Contract> findByIspolnenoTrue() {
        return contractRepository.findAllByIspolneno(true);
    }

    public List<Contract> findByNotIspolnenoSrok() {
        List<Contract> contracts = new ArrayList<>();
        findByIspolnenoFalse().forEach(contract -> {
            if (contract.getDaysOst() >= 0 && contract.getDaysOst() <= 4) {
                contracts.add(contract);
            }
        });
        return contracts;
    }

    public List<Contract> findByNodate() {
        List<Contract> contracts = new ArrayList<>();
        findByIspolnenoFalse().forEach(contract -> {
            if (contract.getRaschet_date() == null) {
                contracts.add(contract);
            }
        });
        return contracts;
    }

    public List<Contract> findByProsrocheno() {
        List<Contract> contracts = new ArrayList<>();
        findByIspolnenoFalse().forEach(contract -> {
            if (contract.getDaysOst() < 0) {
                if (contract.getRaschet_date() != null) {
                    contracts.add(contract);
                }
            }
        });
        return contracts;
    }


    public int getColSize() {
        List<Contract> contracts = findAll();
        return contracts.size();
    }

    public AtomicInteger getColIspolneno() {
        List<Contract> contracts = findAll();
        AtomicInteger ispolneno = new AtomicInteger();
        contracts.forEach(contract -> {
            if (contract.getIspolneno() == true) {
                ispolneno.getAndIncrement();
            }
        });
        return ispolneno;
    }

    public AtomicInteger getColNotispolneno() {
        List<Contract> contracts = findAll();

        AtomicInteger notispolneno = new AtomicInteger();
        contracts.forEach(contract -> {
            if (contract.getIspolneno() != true) {
                notispolneno.getAndIncrement();
            }
        });
        return notispolneno;
    }

    public AtomicInteger getColNotispolnenosrok() {
        List<Contract> contracts = findAll();
        AtomicInteger notispolnenosrok = new AtomicInteger();
        contracts.forEach(contract -> {
            if (contract.getIspolneno() != true) {
                if (contract.getDaysOst() <= 4) {
                    if (contract.getDaysOst() >= 0) {
                        if (contract.getRaschet_date() != null) {
                            notispolnenosrok.getAndIncrement();
                        }
                    }
                }
            }
        });
        return notispolnenosrok;
    }

    public AtomicInteger getColNodate() {
        List<Contract> contracts = findAll();
        AtomicInteger nodate = new AtomicInteger();
        contracts.forEach(contract -> {
            if (contract.getIspolneno() != true) {
                if (contract.getDaysOst() < 0) {
                    if (contract.getRaschet_date() == null) {
                        nodate.getAndIncrement();
                    }
                }
            }

        });
        return nodate;
    }

    public AtomicInteger getColProsrocheno() {
        List<Contract> contracts = findAll();
        AtomicInteger prosrocheno = new AtomicInteger();
        contracts.forEach(contract -> {
            if (contract.getIspolneno() != true) {
                if (contract.getDaysOst() < 0) {
                    if (contract.getRaschet_date() != null) {
                        prosrocheno.getAndIncrement();
                    }

                }
            }
        });
        return prosrocheno;
    }

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    @Async
    @Transactional
    public void sendEmails() {
        List<Contract> contracts = this.findByIspolnenoFalse();
        System.out.println("ColList = " + contracts.size());
        contracts.forEach(contract -> {

            int days = contract.getDaysOst();

            String subject = "\"Предупреждение! Возврат обеспечения исполнения контракта!\"!";
            String text = "Осталось дней: " + days +
                    "\n Наименование контрагента: " + contract.getKontragent().getName() +
                    "\n ИНН контрагента: " + contract.getKontragent().getInn() +
                    "\n Номер контракта: " + contract.getNomGK() +
                    "\n Дата контракта: " + contract.getDateGKRu() +
                    "\n Вид обеспечения: " + contract.getVidObesp().getName() +
                    "\n Сумма: " + contract.getSumOk();

            System.out.println("Дней " + days);
            if (days >= 0 && days <= 4) {

                try {
                    String emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                    System.out.println("Создатель " + emailUser);

                    mailSender.send(
                            zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir()))),
                            subject, text); //сообщение создателю
                    String emailBoss = null;
                    try {
                        emailBoss = zirServise.getEmailBossById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));

                        System.out.println("Босс " + emailBoss);
                        if (emailBoss != null && !emailUser.equals(emailBoss)) {
                            mailSender.send(
                                    emailBoss,
                                    subject, text); //сообщение начальнику отдела
                        }

                    } catch (Exception e) {
                    }


                    for (Notification notification :
                            contract.getNotifications()) {
                        String email = zirServise.getEmailUserById(
                                Integer.parseInt(String.valueOf(notification.getId_user())));
                        System.out.println("Кто то " + email);
                        if (!emailUser.equals(email) && (emailBoss != null && !emailBoss.equals(email))) {
                            mailSender.send(email,
                                    subject, text);//сообщение остальным
                        }
                    }

                /*String emailUser = zirServise.getEmailUserById(1997);
                System.out.println("Создатель " + emailUser);
                mailSender.send(
                        zirServise.getEmailUserById(1997),
                        subject,text); //сообщение мне*/
                } catch (Exception e) {
                    logiService.save(new Logi(
                            "this",
                            "MailError",
                            "Ошибка при отправке сообщения!"));
                }
            }
        });
    }

}
