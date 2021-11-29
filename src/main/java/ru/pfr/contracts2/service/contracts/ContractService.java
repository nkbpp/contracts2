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

/*    public List<Contract> findByfindByNomGK(String nom, String inn) {
        return contractRepository.findByNomGKAndKontragentInn(nom, inn);
    }*/

    public List<Contract> findByfindByNomGK(String name, String inn) {
        return contractRepository.findByNomGKAndKontragentInnScript(name, inn);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    public List<Contract> findAll(int l) {
        return cutTheList(contractRepository.findAllByOrderByIdDesc(), l, COL);
    }

    @Transactional
    public void save(Contract contract) {
        //рассчитываем расчетную дату
        Date date = contract.getDate_ispolnenija_GK();
        if(date!=null){
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE,(contract.getCol_days() + 2) );
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
            int days = ConverterDate.differenceInDays(ras,tec);

            String subject = "\"Предупреждение! Возврат обеспечения исполнения контракта!\"!";
            String text = "Осталось дней: " + days +
                    "\n Наименование контрагента: " + contract.getKontragent().getName() +
                    "\n ИНН контрагента: " + contract.getKontragent().getInn() +
                    "\n Номер контракта: " + contract.getNomGK() +
                    "\n Дата контракта: " + contract.getDateGKRu() +
                    "\n Вид обеспечения: " + contract.getVidObesp().getName() +
                    "\n Сумма: " + contract.getSumOk();

            System.out.println("Дней " + days);
            if(days>=0 && days<=4){

                String emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                System.out.println("Создатель " + emailUser);
                mailSender.send(
                        zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir()))),
                        subject,text); //сообщение создателю
                String emailBoss  = zirServise.getEmailBossById(Integer.parseInt(String.valueOf(contract.getUser().getId_user_zir())));
                System.out.println("Босс " + emailBoss);
                if(!emailUser.equals(emailBoss)) {
                    mailSender.send(
                            emailBoss,
                            subject, text); //сообщение начальнику отдела
                }

                for (Notification notification:
                        contract.getNotifications()) {
                    String email = zirServise.getEmailUserById(
                            Integer.parseInt(String.valueOf(notification.getId_user())));
                    System.out.println("Кто то " +email);
                    if(!emailUser.equals(email) && !emailBoss.equals(email)) {
                        mailSender.send(email,
                                subject, text);//сообщение остальным
                    }
                }
            }


        });

    }
}
