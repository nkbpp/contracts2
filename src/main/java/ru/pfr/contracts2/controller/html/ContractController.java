package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/main"})
public class ContractController {

    private final ContractService contractService;
    private final VidObespService vidObespService;
    private final KontragentService kontragentService;
    private final ZirServise zirServise;

    @GetMapping("/getTable")  //Перелистывания TODO в зависимости от пользователя и отдела
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Model model){
        param = param==null?1:param;

        List<Contract> contracts = contractService.findAll(param);
        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart", (param-1) * contractService.getCOL());
        return "fragment/table :: table";
    }

    @GetMapping("/dopTable")  //дополнительная информация
    public String dopTable(
            @RequestParam(defaultValue = "") Long id,
            @AuthenticationPrincipal User user,
            Model model){

        Contract contract = contractService.findById(id);
        model.addAttribute("contract", contract);
        return "fragment/modalKontragent :: tableContractDop";
    }

    @GetMapping("/findTable") //TODO в зависимости от пользователя и отдела
    public String findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByINN,
            @RequestParam(defaultValue = "") Boolean poleFindByIspolneno,
            @RequestParam(defaultValue = "") Boolean poleFindByNotIspolneno,
            @AuthenticationPrincipal User user,
            Model model){
        List<Contract> contracts;
        if(poleFindByNomGK.equals("") && poleFindByINN.equals("")){
            contracts = contractService.findAll();
        } else {
            contracts = contractService.findByfindByNomGK(poleFindByNomGK, poleFindByINN); //TODO добавить ИНН
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
    }

    @GetMapping("/vievTable")
    public String vievTable(){
        return "fragment/table :: vievTable";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                       Model model){

        model.addAttribute("kontragent", kontragentService.findAllwithPusto());
        model.addAttribute("kontragent2", kontragentService.findAll());
        model.addAttribute("vidObesp", vidObespService.findAllwithPusto());

        model.addAttribute("nameBoss",
                zirServise.getNameBossById(Integer.parseInt(String.valueOf(user.getId_user_zir()))));

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/updateViev")
    public String updateViev(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
                      Model model){

        model.addAttribute("kontragent", kontragentService.findAllwithPusto());
        model.addAttribute("kontragent2", kontragentService.findAll());
        model.addAttribute("vidObesp", vidObespService.findAllwithPusto());
        model.addAttribute("contract", contractService.findById(id));

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/getnotification")
    public String getnotification(@AuthenticationPrincipal User user,
                      Model model){

        List<User> users = new ArrayList<>(zirServise.getFindAllOtdelByIdAddPusto(user.getId_user_zir()));

        //Map<String,String> otdel =  zirServise.getFindAllOtdel();
        //model.addAttribute("otdel", otdel);

        model.addAttribute("users", users);


        return "fragment/contractAdd :: notifications";
    }


}
