package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contractIT.mapper.ContractItMapper;
import ru.pfr.contracts2.entity.contracts.Notification;
import ru.pfr.contracts2.global.GetOtdel;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/dop"})
public class ContractDopController {

    private final ContractItService contractItService;
    private final ZirServise zirServise;
    private final ContractItMapper contractItMapper;

/*    private final BudgetClassificationService budgetClassificationService;
    private final BudgetClassificationResponseMapper budgetClassificationResponseMapper;

    @ModelAttribute(name = "budgetClassification")
    public List<BudgetClassificationDto> budgetClassification(
            Authentication authentication
    ) {
        return budgetClassificationService.findByRole(GetOtdel.get(authentication))
                .stream()
                .map(budgetClassificationResponseMapper)
                .toList();
    }*/

    @ModelAttribute(name = "notifications")
    public List<Notification> notifications(
            Authentication authentication
    ) {

        return switch (GetOtdel.get(authentication)) {
            case "IT" -> zirServise.getNotification("148", "149");
            case "RSP" -> List.of(); //todo добавить отделы для выбора ответственных
            default -> List.of();
        };
    }

    @GetMapping("/vievTable")
    public String vievTable(
            Model model
    ) {
        model.addAttribute("findContractName", "findContractIt");
        return "fragment/it/viev :: vievTable";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            Authentication authentication,
            Model model) {

        int skip = col * (param == null ? 0 : param - 1);
        List<ContractIT> contractITs = contractItService.findAll(GetOtdel.get(authentication))
                .stream()
                .skip(skip)
                .limit(col)
                .toList();

        model.addAttribute("contracts", contractITs
                .stream()
                .map(contractItMapper::toDto)
                .collect(Collectors.toList()));
        model.addAttribute("paramstart",
                skip);
        model.addAttribute("col",
                col);

        return "fragment/it/viev :: table";

    }

    @GetMapping("/add")
    public String add() {
        return "fragment/it/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev() {
        return "fragment/it/add :: addviev";
    }


}
