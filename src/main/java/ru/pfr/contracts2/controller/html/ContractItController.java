package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.contracts.entity.Notification;

import ru.pfr.contracts2.global.GetOtdel;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/it"})
public class ContractItController {

    private final ZirServise zirServise;

    @ModelAttribute(name = "notifications")
    public List<Notification> notifications(
            Authentication authentication
    ) {
        return zirServise.getNotification("148", "149");/*switch (GetOtdel.get(authentication)) {
            case "IT" -> zirServise.getNotification("148", "149");
            case "RSP" -> List.of(); //todo добавить отделы для выбора ответственных
            default -> List.of();
        };*/
    }

    @GetMapping("/vievTable")
    public String vievTable(
            Model model
    ) {
        model.addAttribute("findContractName", "findContractIt");
        return "fragment/it/viev :: vievTable";
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
