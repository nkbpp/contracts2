package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.Contract;
import ru.pfr.contracts2.entity.contracts.VidObesp;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/main"})
public class ContractController {

    private final ContractService contractService;
    private final VidObespService vidObespService;
    private final UserService userService;

    @GetMapping("/getTable")
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @AuthenticationPrincipal User user,
            Model model){
        param = param==null?1:param;

        List<Contract> contracts = contractService.findAll(param);
        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart", (param-1) * contractService.getCOL());
/*         model.addAttribute("activeparam", param);
        int params[] = new int[5];
        if(param<=3){
            for (int i = 1; i <= params.length; i++) {
                params[i-1]=i;
            }
        } else {
            for (int i = param - 3; i < param - 3 + params.length; i++) {
                params[i-1]=i;
            }
        }
        model.addAttribute("params", params);*/
        return "fragment/table :: table";
    }

    @GetMapping("/vievTable")
    public String vievTable(){
        return "fragment/table :: vievTable";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                       Model model){

        List<VidObesp> vidObesps = new ArrayList<>();
        vidObesps.add(new VidObesp(0L,""));
        vidObesps.addAll(vidObespService.findAll());
        model.addAttribute("vidObesp", vidObesps);

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/updateViev")
    public String updateViev(
            @RequestParam Long id,
            @AuthenticationPrincipal User user,
                      Model model){

        List<VidObesp> vidObesps = new ArrayList<>();
        vidObesps.add(new VidObesp(0L,""));
        vidObesps.addAll(vidObespService.findAll());
        model.addAttribute("vidObesp", vidObesps);

        model.addAttribute("contract", contractService.findById(id));

        return "fragment/contractAdd :: contractAdd";
    }

    @GetMapping("/getnotification")
    public String getnotification(@AuthenticationPrincipal User user,
                      Model model){

        List<User> users = new ArrayList<>();
        users.addAll(userService.findAll());
        model.addAttribute("users", users);

        return "fragment/contractAdd :: notifications";
    }


}
