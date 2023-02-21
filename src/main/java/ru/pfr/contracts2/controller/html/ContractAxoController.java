package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.service.it.ContractItService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/axo"})
public class ContractAxoController {

    private static final String ROLE = "AXO";

    private final ContractItService contractItService;

    @GetMapping("/vievTable")
    public String vievTable(
            Model model
    ) {
        model.addAttribute("findContractName", "findContractAxo");
        return "fragment/axo/viev :: vievTableAxo";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            Model model) {

        param = param == null ? 0 : param - 1;

        List<ContractIT> contractITs = contractItService.findAllcut(param, ROLE);

        model.addAttribute("contracts", contractITs);
        model.addAttribute("paramstart",
                (param) * contractItService.getCOL());

        return "fragment/axo/viev :: table";

    }

    @GetMapping("/getTable2")  //TODO
    public String getTable2(
            @RequestParam(defaultValue = "") Integer param,
            Model model) {

        param = param == null ? 0 : param - 1;

        List<ContractIT> contractITs = contractItService.findAllcut(param, ROLE);

        model.addAttribute("contracts", contractITs);
        model.addAttribute("paramstart",
                (param) * contractItService.getCOL());

        return "fragment/axo/viev2 :: table2";

    }

    @GetMapping("/add")
    public String add() {

        return "fragment/axo/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            //@PathVariable Long id
    ) {

        return "fragment/axo/add :: addviev";
    }


}
