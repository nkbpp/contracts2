package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.Kontragent;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.VidObesp;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.ContractDkService;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.contracts.VidObespService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class ContractDkController {

    private final ContractDkService contractDkService;
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

        return "fragment/contractAddDk :: contractAdd";
    }

    @GetMapping("/updateViev")
    public String updateViev(
            @RequestParam Long id,
            Model model) {
        model.addAttribute("contract", contractDkService.findById(id));

        return "fragment/contractAddDk :: contractAdd";
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
        return "fragment/contractNotification :: notifications";
    }

    @GetMapping("/getProgress")
    public String getProgress(
            @RequestParam Long id,
            Model model) {

        model.addAttribute("c", contractDkService.findById(id));

        return "fragment/table :: progress";
    }
}
