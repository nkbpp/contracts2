package ru.pfr.contracts2.service.contracts;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.MyDocuments;
import ru.pfr.contracts2.entity.contracts.Notification;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.repository.contracts.ContractRepository;
import ru.pfr.contracts2.service.mail.MailSender;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.*;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ContractService {

    private final MailSender mailSender;
    private final ZirServise zirServise;
    final ContractRepository contractRepository;

    private final int COL = 30;

    public Contract findById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    public List<Contract> findByfindByNomGK(String nom, String inn) {
        return contractRepository.findByNomGKAndKontragentInn(nom, inn);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findAll(int l) {
        return cutTheList(contractRepository.findAll(), l, COL);
    }

    @Transactional
    public void save(Contract contract) {
        //рассчитываем расчетную дату
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(contract.getDate_ispolnenija_GK());
        calendar.add(Calendar.DATE,contract.getCol_days());
        contract.setRaschet_date(calendar.getTime());

        contract.setDate_update(new Date());

        for (MyDocuments d : contract.getMyDocuments()) { //добавляем ссылку на контракт в MyDocuments
            d.setContract(contract);
        }
        for (Notification n : contract.getNotifications()) { //добавляем ссылку на контракт в Notification
            n.setContract(contract);
        }
        contractRepository.save(contract);
    }

    @Transactional
    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public int getCOL() {
        return COL;
    }

    //для обрезки
    private List<Contract> cutTheList(List<Contract> contracts, int list, int mnoj) {
        List<Contract> contracts2 = new ArrayList<>();

        int start = mnoj*(list-1);
        int end = mnoj*(list-1) + mnoj;

        for (int i = start; i < end && i<contracts.size() ; i++) {
            contracts2.add(contracts.get(i));
        }

        return contracts2;
    }

    public List<Contract> findByIspolnenoFalse() {
        return contractRepository.findAllByIspolneno(false);
    }

    @Scheduled(cron = "0 0 8 * * MON-FRI")
    @Async
    @Transactional
    public void sendEmails() {
        List<Contract> contracts = this.findByIspolnenoFalse();
        System.out.println("ColList = " + contracts.size());
        contracts.forEach(contract -> {
            Date ras = contract.getRaschet_date();
            Date tec = ConverterDate.stringToDate(ConverterDate.datetostring_yyyyMMdd(new Date()));
            int days = ConverterDate.differenceInDays(tec,ras);

            String subject = "предупреждение!";
            String text = "НомерГК " +
                    contract.getNomGK() +
                    " дней осталось " + days;

            if(days>=0 && days<=4){
                //тест почты
                for (Notification notification:
                     contract.getNotifications()) {
                    mailSender.send(zirServise.getEmailUserById(
                            Integer.valueOf(String.valueOf(notification.getId_user()))),
                            subject,text);//сообщение остальным
                }

                mailSender.send(
                        zirServise.getEmailUserById(Integer.valueOf(String.valueOf(contract.getUser().getId_user_zir()))),
                        subject,text); //сообщение создателю
                mailSender.send(
                        zirServise.getEmailBossById(Integer.valueOf(String.valueOf(contract.getUser().getId_user_zir()))),
                        subject,text); //сообщение начальнику отдела
            }

            /*System.out.println("Разница между датами в днях: " + days +
            " ras = " + ras +
                    " tec = " + tec);*/
        });

    }
}
