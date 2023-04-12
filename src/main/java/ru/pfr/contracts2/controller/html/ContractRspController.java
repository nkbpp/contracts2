package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.KontragentRsp;
import ru.pfr.contracts2.entity.contracts.contractRsp.entity.VidObespRsp;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.*;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp"})
public class ContractRspController {

    private final ContractRspService contractRspService;
    private final VidObespRspService vidObespService;
    private final KontragentRspService kontragentService;
    private final ZirServise zirServise;

    @ModelAttribute(name = "vidObesp")
    public List<VidObespRsp> vidObesp() {
        return vidObespService.findAllwithPusto();
    }

    @ModelAttribute(name = "kontragent")
    public List<KontragentRsp> kontragent() {
        return kontragentService.findAllwithPusto();
    }

    @ModelAttribute(name = "kontragent2")
    public List<KontragentRsp> kontragent2() {
        return kontragentService.findAll();
    }

    @GetMapping("/dopTable")  //дополнительная информация
    public String dopTable() {
        return "fragment/modalKontragent :: tableContractDop";
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
        return "fragment/contractAddRsp :: contractAdd";
    }

    @GetMapping("/updateViev")
    public String updateViev(
            @RequestParam Long id,
            Model model) {
        model.addAttribute("contract", contractRspService.findById(id));
        return "fragment/contractAddRsp :: contractAdd";
    }

    @GetMapping("/getnotification")
    public String getnotification(@AuthenticationPrincipal User user,
                                  Model model) {

        /*if (!user.getId_user_zir().equals(147L)) {
            users.addAll(zirServise.getFindAllOtdelByIdAddPusto(147L));
        }*/
        List<User> users = new ArrayList<>(zirServise.getFindAllOtdelByIdAddPusto(user.getId_user_zir()));

        model.addAttribute("users", users);
        return "fragment/contractNotification :: notifications";
    }

    @GetMapping("/getProgress")
    public String getProgress(
            @RequestParam Long id,
            Model model) {

        model.addAttribute("c", contractRspService.findById(id));

        return "fragment/table :: progress";
    }
}
