package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.services.contracts.VidObespService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/main"})
public class VidObespController {

    private final VidObespService vidObespService;

    @GetMapping("/vidObespSpisokViev")
    public String vidObespSpisokViev(
            Model model) {
        model.addAttribute("vidObesps", vidObespService.findAll());
        model.addAttribute("type", "vidObesp");
        return "fragment/spisokfrag :: spisokfrag";
    }

}
