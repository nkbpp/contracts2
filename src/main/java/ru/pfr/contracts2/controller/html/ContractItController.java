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
@RequestMapping(value = { "/contract/it"})
public class ContractItController {

    private final ContractItService contractItService;

    private final LogiService logiService;

    @GetMapping("/vievTable")
    public String vievTable(
            @AuthenticationPrincipal User user,
            Model model
    ){
        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы it контрактов"));
       // model.addAttribute("paginationContractName", "paginationItContract");
        model.addAttribute("findContractName", "findContractIt");
        return "fragment/it/viev :: vievTable";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model){

        //String role = User.getRole(authentication);
        String role = "IT";

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы it контрактов на странице " + param));

        param = param==null?1:param;

        List<ContractIT> contractITs = contractItService.findAll(param, role);

        model.addAttribute("contracts", contractITs);
        //model.addAttribute("paginationContractName", "paginationItContract");
        model.addAttribute("paramstart",
                (param-1) * contractItService.getCOL());

        return "fragment/it/viev :: table";

    }

/*    @GetMapping("/getTable2")  //TODO
    public String getTable2(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model){

        //String role = User.getRole(authentication);
        String role = "IT";

        param = param==null?1:param;

        List<ContractIT> contractITs = contractItService.findAll(param, role);

        model.addAttribute("contracts", contractITs);
        model.addAttribute("paramstart",
                (param-1) * contractItService.getCOL());

        return "fragment/it/viev2 :: table2";

    }*/

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
        String role = "IT";

        List<ContractIT> contracts;
        if(poleFindByNomGK.equals("") && poleFindByKontragent.equals("")){
            contracts = contractItService.findAllByRole(role);
        } else {
            contracts = contractItService
                    .findByNomGK(poleFindByNomGK, poleFindByKontragent, role);
        }

        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart", 0);
        return "fragment/it/viev :: table";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                       Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы добавления it контракта"));

        return "fragment/it/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы изменения it контракта с id = " + id));

        return "fragment/it/add :: addviev";
    }



}
