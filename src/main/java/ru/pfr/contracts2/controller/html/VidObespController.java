package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.contracts.VidObespService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class VidObespController {

    private final VidObespService vidObespService;

    @GetMapping("/vidObespSpisokViev")
    public String vidObespSpisokViev(
            @AuthenticationPrincipal User user,
            Model model) {
        model.addAttribute("vidObesps", vidObespService.findAll());
        model.addAttribute("type", "vidObesp");
        return "fragment/spisokfrag :: spisokfrag";
    }

}
