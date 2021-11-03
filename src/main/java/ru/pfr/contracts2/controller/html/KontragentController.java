package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.KontragentService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/main/kontragent"})
public class KontragentController {

    private final KontragentService kontragentService;

    @GetMapping("/kontragentViev")
    public String kontragentViev(
            @AuthenticationPrincipal User user,
            Model model){
        model.addAttribute("kontragents",kontragentService.findAll());
        return "fragment/kontragent :: kontragent";
    }

}
