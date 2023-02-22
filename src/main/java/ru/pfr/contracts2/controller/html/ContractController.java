package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.Kontragent;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class ContractController {

    private final ContractService contractService;
    private final VidObespService vidObespService;
    private final KontragentService kontragentService;
    private final ZirServise zirServise;

    @ModelAttribute(name = "vidObesp")
    public List<VidObesp> vidObesp() {
        return vidObespService.findAllwithPusto();
    }

    @ModelAttribute(name = "kontragent")
    public List<Kontragent> kontragent() {
        return kontragentService.findAllwithPusto();
    }

    @ModelAttribute(name = "kontragent2")
    public List<Kontragent> kontragent2() {
        return kontragentService.findAll();
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @RequestParam(defaultValue = "0") String statIspolneno,
            @RequestParam(defaultValue = "0") String statNotIspolneno,
            @RequestParam(defaultValue = "0") String statNotIspolnenoSrok,
            @RequestParam(defaultValue = "0") String statNodate,
            @RequestParam(defaultValue = "0") String statProsrocheno,
            Model model) {

        param = param == null ? 0 : param - 1;

        List<Contract> contracts;
        if (statIspolneno.equals("1")) {
            contracts = contractService.findByIspolnenoTrue();
        } else if (statNotIspolneno.equals("1")) {
            contracts = contractService.findByIspolnenoFalse();
        } else if (statNotIspolnenoSrok.equals("1")) {
            contracts = contractService.findByNotIspolnenoSrok();
        } else if (statNodate.equals("1")) {
            contracts = contractService.findByNodate();
        } else if (statProsrocheno.equals("1")) {
            contracts = contractService.findByProsrocheno();
        } else {
            contracts = contractService.findAll(param);
        }

        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart",
                (param) * contractService.getCOL());

        return "fragment/table :: table";
    }


    @GetMapping("/dopTable")  //дополнительная информация
    public String dopTable(
            @RequestParam(defaultValue = "") Long id,
            Model model) {

        Contract contract = contractService.findById(id);
        model.addAttribute("contract", contract);
        return "fragment/modalKontragent :: tableContractDop";
    }

    @GetMapping("/findTable")
    public String findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByINN,
            @RequestParam(defaultValue = "") Boolean poleFindByIspolneno,
            @RequestParam(defaultValue = "") Boolean poleFindByNotIspolneno,
            Model model) {

        List<Contract> contracts;
        if (poleFindByNomGK.equals("") && poleFindByINN.equals("")) {
            contracts = contractService.findAll();
        } else {
            contracts = contractService.findByfindByNomGK(
                    poleFindByNomGK, poleFindByINN
            );
        }
        List<Contract> contracts2 = new ArrayList<>();
        contracts.forEach(contract -> {
            if (!poleFindByIspolneno && !poleFindByNotIspolneno) { //todo
            } else if (contract.getIspolneno() == poleFindByIspolneno) {
                contracts2.add(contract);
            } else if (contract.getIspolneno() == !poleFindByNotIspolneno) {
                contracts2.add(contract);
            }
        });

        model.addAttribute("contracts", contracts2);
        model.addAttribute("paramstart", 0);
        return "fragment/table :: table";
    }

    @GetMapping("/vievTable")
    public String vievTable() {
        return "fragment/table :: vievTable";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                      Model model) {

        model.addAttribute("nameBoss",
                zirServise.getNameBossById
                        (Integer.parseInt(String.valueOf(user.getId_user_zir())))
        );

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/updateViev")
    public String updateViev(
            @RequestParam Long id,
            Model model) {
        model.addAttribute("contract", contractService.findById(id));

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/getnotification")
    public String getnotification(@AuthenticationPrincipal User user,
                                  Model model) {
        List<User> users = new ArrayList<>();

        if (!user.getId_user_zir().equals(147L)) {
            users.addAll(zirServise.getFindAllOtdelByIdAddPusto(147L));
        }
        users.addAll(zirServise.getFindAllOtdelByIdAddPusto(user.getId_user_zir()));

        model.addAttribute("users", users);
        return "fragment/contractAdd :: notifications";
    }


    @GetMapping("/getProgress")
    public String getProgress(
            @RequestParam Long id,
            Model model) {

        model.addAttribute("c", contractService.findById(id));

        return "fragment/table :: progress";
    }

    @GetMapping("/stat")
    public String stat(
            Model model) {

        model.addAttribute("size",
                contractService.getColSize());
        model.addAttribute("ispolneno",
                contractService.getColIspolneno());
        model.addAttribute("notispolneno",
                contractService.getColNotispolneno());
        model.addAttribute("notispolnenosrok",
                contractService.getColNotispolnenosrok());
        model.addAttribute("nodate",
                contractService.getColNodate());
        model.addAttribute("prosrocheno",
                contractService.getColProsrocheno());

        return "fragment/stat :: stat";
    }


}
