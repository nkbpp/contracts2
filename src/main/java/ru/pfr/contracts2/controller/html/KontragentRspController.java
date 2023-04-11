package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.service.contracts.KontragentRspService;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/rsp/kontragent"})
public class KontragentRspController {

    private final KontragentRspService kontragentService;

    @GetMapping("/kontragentViev")
    public String kontragentViev() {

        return "fragment/kontragentRsp :: kontragent";
    }

    @GetMapping("/modalKontragentViev") //контрагент
    public String modalKontragentViev() {

        return "fragment/modalKontragent :: modalKontragentTable";
    }

    @GetMapping("/findKontragent")
    public String findKontragent(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String inn,
            Model model) {
        model.addAttribute("kontragent2", kontragentService.findByNameAndInn(name, inn));
        return "fragment/modalKontragent :: modalKontragentTable";
    }

}
