package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/axo"})
public class ContractAxoController {

    private final ContractItService contractItService;

    private final LogiService logiService;

    @GetMapping("/vievTable")
    public String vievTable(
            @AuthenticationPrincipal User user,
            Model model
    ){
        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы axo контрактов"));
        //model.addAttribute("paginationContractName", "paginationAxoContract");
        model.addAttribute("findContractName", "findContractAxo");
        return "fragment/axo/viev :: vievTableAxo";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model){

        //String role = User.getRole(authentication);
        String role = "AXO";

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы axo контрактов на странице " + param));

        param = param==null?1:param;

        List<ContractIT> contractITs = contractItService.findAll(param, role);

        model.addAttribute("contracts", contractITs);
        //model.addAttribute("paginationContractName", "paginationAxoContract");
        model.addAttribute("paramstart",
                (param-1) * contractItService.getCOL());

        return "fragment/axo/viev :: table";

    }

    @GetMapping("/getTable2")  //TODO
    public String getTable2(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model){

        //String role = User.getRole(authentication);
        String role = "AXO";

        param = param==null?1:param;

        List<ContractIT> contractITs = contractItService.findAll(param, role);

        model.addAttribute("contracts", contractITs);
        model.addAttribute("paramstart",
                (param-1) * contractItService.getCOL());

        return "fragment/axo/viev2 :: table2";

    }

    @GetMapping("/findTable")
    public String findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByKontragent,
            Authentication authentication,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"Find",
                "Поиск в таблице контрактов"));

        //String role = User.getRole(authentication);
        String role = "AXO";

        List<ContractIT> contracts;
        if(poleFindByNomGK.equals("") && poleFindByKontragent.equals("")){
            contracts = contractItService.findAllByRole(role);
        } else {
            contracts = contractItService
                    .findByNomGK(poleFindByNomGK, poleFindByKontragent, role);
        }

        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart", 0);
        return "fragment/axo/viev :: table";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                      Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы добавления axo контракта"));

        return "fragment/axo/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы изменения axo контракта с id = " + id));

        return "fragment/axo/add :: addviev";
    }



}
