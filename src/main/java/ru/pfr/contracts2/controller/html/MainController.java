package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.NotificationService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.user.RayonService;
import ru.pfr.contracts2.service.user.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract"})
public class MainController {

    final RayonService rayonService;
    final UserService userService;

    final ContractService contractService;
    final NotificationService notificationService;
    final VidObespService vidObespService;

    @GetMapping("/main")
    public String main(Model model){

        List<Contract> contracts = contractService.findAll();

        model.addAttribute("contracts",contracts);

        return "main";
    }

}
