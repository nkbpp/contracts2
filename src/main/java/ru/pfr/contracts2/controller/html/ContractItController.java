package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/it"})
public class ContractItController {

    private final ContractItService contractItService;

    private final LogiService logiService;

    @GetMapping("/vievTable")
    public String vievTable(@AuthenticationPrincipal User user){
        logiService.save(new Logi(user.getLogin(),"View","Показ таблицы it контрактов"));
        return "fragment/it/viev :: vievTable";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"View","Показ таблицы it контрактов на странице " + param));

        param = param==null?1:param;

        List<ContractIT> contractITs = contractItService.findAll(param);

        model.addAttribute("contracts", contractITs);
        model.addAttribute("paramstart", (param-1) * contractItService.getCOL());

        return "fragment/it/viev :: table";
    }

/*    @GetMapping("/findTable")
    public String findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByINN,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"Find","Поиск в таблице контрактов"));

        List<Contract> contracts;
        if(poleFindByNomGK.equals("") && poleFindByINN.equals("")){
            contracts = contractService.findAll();
        } else {
            contracts = contractService.findByfindByNomGK(poleFindByNomGK, poleFindByINN);
        }
        List<Contract> contracts2  = new ArrayList<>();
        contracts.forEach(contract -> {
            if(!poleFindByIspolneno && !poleFindByNotIspolneno) {
            } else if(contract.getIspolneno()==poleFindByIspolneno){
                contracts2.add(contract);
            } else if(contract.getIspolneno()==!poleFindByNotIspolneno){
                contracts2.add(contract);
            }
        });

        model.addAttribute("contracts", contracts2);
        model.addAttribute("paramstart", 0);
        return "fragment/table :: table";
    }*/

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                       Model model){
        logiService.save(new Logi(user.getLogin(),"View","Показ страницы добавления it контракта"));

        return "fragment/it/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            @PathVariable Long id,
            //@RequestParam Long id,
            @AuthenticationPrincipal User user,
                      Model model){

        logiService.save(new Logi(user.getLogin(),"View","Показ страницы изменения it контракта с id = " + id));

        //model.addAttribute("contract", contractService.findById(id));

        return "fragment/it/add :: addviev";
    }



}
