package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.services.contracts.VidObespRspService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp"})
public class VidObespRspController {

    private final VidObespRspService vidObespService;

    @GetMapping("/vidObespSpisokViev")
    public String vidObespSpisokViev(
            Model model) {
        model.addAttribute("vidObesps", vidObespService.findAll());
        model.addAttribute("type", "vidObesp");
        return "fragment/vidObespRsp :: spisokfrag";
    }

}
