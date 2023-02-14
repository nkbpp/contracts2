package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.entity.contractIT.BudgetClassificationResponseMapper;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contractIT.dto.BudgetClassificationDto;
import ru.pfr.contracts2.entity.contractIT.mapper.ContractItMapper;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.repository.it.BudgetClassificationRepository;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp"})
public class ContractRspController {

    private static final String ROLE = "RSP";

    private final ContractItService contractItService;
    private final ZirServise zirServise;
    private final LogiService logiService;
    private final ContractItMapper contractItMapper;

    private final BudgetClassificationRepository budgetClassificationRepository;
    private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;

    @ModelAttribute(name = "budgetClassification")
    public List<BudgetClassificationDto> budgetClassification() {
        return budgetClassificationRepository.findByRole(ROLE)
                .stream()
                .map(budgetClassificationResponseMapper::apply)
                .toList();
    }

    @GetMapping("/vievTable")
    public String vievTable(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        logiService.save(new Logi(user.getLogin(), "View",
                "Показ таблицы rsp контрактов"));
        model.addAttribute("findContractName", "findContractIt");
        model.addAttribute(
                "notifications",
                zirServise.getNotification("148", "149")
        );
        return "fragment/it/viev :: vievTable";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(user.getLogin(), "View",
                "Показ таблицы rsp контрактов на странице " + param));

        int skip = col * (param == null ? 0 : param - 1);
        List<ContractIT> contractITs = contractItService.findAll(ROLE)
                .stream()
                .skip(skip)
                .limit(col)
                .toList();

        model.addAttribute("contracts", contractITs
                .stream()
                .map(contractItMapper::toDto)
                .toList());
        model.addAttribute("paramstart",
                skip);
        model.addAttribute("col",
                col);

        return "fragment/it/viev :: table";

    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                      Model model) {

        logiService.save(new Logi(user.getLogin(), "View",
                "Показ страницы добавления rsp контракта"));

        model.addAttribute(
                "notifications",
                zirServise.getNotification("148", "149")
        );

        return "fragment/it/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model) {

        logiService.save(new Logi(user.getLogin(), "View",
                "Показ страницы изменения new контракта с id = " + id));

        model.addAttribute(
                "notifications",
                zirServise.getNotification("148", "149")
        );

        return "fragment/it/add :: addviev";
    }


}
