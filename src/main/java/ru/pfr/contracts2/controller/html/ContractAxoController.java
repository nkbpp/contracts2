package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/axo"})
public class ContractAxoController {

    @GetMapping("/vievTable")
    public String vievTable(
            Model model
    ) {
        model.addAttribute("findContractName", "findContractAxo");
        return "fragment/axo/viev :: vievTableAxo";
    }

    @GetMapping("/add")
    public String add() {
        return "fragment/axo/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
    ) {
        return "fragment/axo/add :: addviev";
    }


}
