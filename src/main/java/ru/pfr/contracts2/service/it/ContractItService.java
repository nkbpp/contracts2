package ru.pfr.contracts2.service.it;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractITSpecification;
import ru.pfr.contracts2.entity.contracts.entity.Contract;
import ru.pfr.contracts2.entity.contracts.entity.ContractSpecification;
import ru.pfr.contracts2.entity.contracts.entity.Notification;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.repository.it.ContractItRepository;
import ru.pfr.contracts2.service.mail.MailSender;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableAsync
@Transactional
public class ContractItService {

    final ContractItRepository contractItRepository;

    private final ZirServise zirServise;

    private final MailSender mailSender;

    public ContractIT findById(Long id) {
        return contractItRepository.findById(id).orElse(null);
    }

    public List<ContractIT> findAll() {
        return contractItRepository.findAll();
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification) {
        return contractItRepository.findAll(specification);
    }

    public List<ContractIT> findAll(Specification<ContractIT> specification, Pageable pageable) {
        return contractItRepository.findAll(specification, pageable).getContent();
    }

    public void save(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    public void update(ContractIT contractIT) {
        contractItRepository.save(contractIT);
    }

    public void delete(Long id) {
        contractItRepository.deleteById(id);
    }

    //@Scheduled(cron = "0 20 16 * * MON-FRI")
    //@Scheduled(fixedRate = 60000)
    @Scheduled(cron = "0 0 8 * * MON-FRI")
    @Async
    @Transactional
    public void sendEmails() {
        final var nowDate = LocalDate.now();
        var lastDay = nowDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        var contractITS = this.findAll(
                        ContractITSpecification.statusGKEquals(
                                StatusGk.CURRENT
                        )
                ).stream()
                .filter(
                        contractIT -> {
                            var dateGKpo = contractIT.getDateGKpo().toLocalDate();
                            return dateGKpo != null && contractIT.getIdzirot() != null && (nowDate.isBefore(dateGKpo) || nowDate.isEqual(dateGKpo));
                        }
                ).toList(); //действующие контракты у которых срок не вышел

        contractITS
                .forEach(
                        contractIT -> {
                            var dayCheck = (contractIT.getDayNotification() == null || contractIT.getDayNotification() > lastDay) ?
                                    lastDay :
                                    contractIT.getDayNotification();
                            var dayCheckEnd = LocalDate.of(nowDate.getYear(), nowDate.getMonthValue(), dayCheck);
                            var dayCheckStart = dayCheckEnd.minusDays(5L); //предупреждать за 5 дней
                            if (
                                    (nowDate.isBefore(dayCheckEnd) || nowDate.isEqual(dayCheckEnd)) &&
                                            (nowDate.isAfter(dayCheckStart) || nowDate.isEqual(dayCheckStart))
                            ) {
                                var month = switch (nowDate.getMonthValue()) {
                                    case 1 -> contractIT.getMonths().getMonth1();
                                    case 2 -> contractIT.getMonths().getMonth2();
                                    case 3 -> contractIT.getMonths().getMonth3();
                                    case 4 -> contractIT.getMonths().getMonth4();
                                    case 5 -> contractIT.getMonths().getMonth5();
                                    case 6 -> contractIT.getMonths().getMonth6();
                                    case 7 -> contractIT.getMonths().getMonth7();
                                    case 8 -> contractIT.getMonths().getMonth8();
                                    case 9 -> contractIT.getMonths().getMonth9();
                                    case 10 -> contractIT.getMonths().getMonth10();
                                    case 11 -> contractIT.getMonths().getMonth11();
                                    default -> contractIT.getMonths().getMonth12();
                                };
                                if (month == 0) { //отправить письмо
                                    String subject = "\"Напоминание о оплате! Обеспечения исполнения контракта!\"!";
                                    String text =
                                            "\n Наименование контракта: " + contractIT.getNomGK() +
                                                    "\n Контрагент: " + contractIT.getKontragent() +
                                                    "\n Действие ГК с: " + contractIT.getDateGKs().toLocalDate() +
                                                    "\n Действие ГК по: " + contractIT.getDateGKpo().toLocalDate();

                                    String emailUser = zirServise.getEmailUserById(Integer.parseInt(String.valueOf(contractIT.getIdzirot())));
                                    mailSender.send(emailUser, subject, text); //сообщение создателю
                                }
                            }
                        }
                );
    }


}
