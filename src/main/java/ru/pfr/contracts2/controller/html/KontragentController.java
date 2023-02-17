package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contracts.Kontragent;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.KontragentService;
import ru.pfr.contracts2.service.log.LogiService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main/kontragent"})
public class KontragentController {

    private final KontragentService kontragentService;
    private final LogiService logiService;

    @ModelAttribute(name = "kontragents")
    public List<Kontragent> kontragents(
            Authentication authentication
    ) {
        return kontragentService.findAll();
    }

    @GetMapping("/kontragentViev")
    public String kontragentViev(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(user.getLogin(), "View", "Страница контрагенты"));
        return "fragment/kontragent :: kontragent";
    }

    @GetMapping("/modalKontragentViev") //контрагент
    public String modalKontragentViev(
            @RequestParam Integer param,
            @AuthenticationPrincipal User user,
            Model model) {

        model.addAttribute("kontragent2", kontragentService.findAll(param - 1));
        return "fragment/modalKontragent :: modalKontragentTable";
    }

    @GetMapping("/findKontragent")
    public String findKontragent(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String inn,
            @AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("kontragent2", kontragentService.findByNameAndInn(name, inn));
        logiService.save(new Logi(user.getLogin(), "Поиск контрагента name=" + name + " inn=" + inn));
        return "fragment/modalKontragent :: modalKontragentTable";
    }

}
