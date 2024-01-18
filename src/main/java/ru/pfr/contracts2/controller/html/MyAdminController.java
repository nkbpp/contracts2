package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.admin.Adminparam;

import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.services.admin.AdminparamService;
import ru.pfr.contracts2.services.log.LogiService;
import ru.pfr.contracts2.services.user.UserService;

import java.util.List;

@Controller
@RequestMapping(value = {"/contract/admin"})
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class MyAdminController {

    private final UserService userService;
    private final LogiService logiService;
    private final AdminparamService adminparamService;

    @GetMapping("/admin")
    public String adminstart(
            @AuthenticationPrincipal User user,
            Model model) {

        model.addAttribute("user", user);

        Adminparam adminparam = adminparamService.findByAdminparam();
        model.addAttribute("adminparam", adminparam);

        List<User> logerrs = userService.findAll();
        model.addAttribute("logerrs", logerrs);

        return "admin";
    }

    @GetMapping("/adminupdate")
    public String adminupdate(
            @RequestParam Long kolpopitok,
            @RequestParam Long koefpopitok,
            @RequestParam Long block,
            @AuthenticationPrincipal User user,
            Model model) {

        model.addAttribute("user", user);

        //Adminparam adminparam = new Adminparam(kolpopitok, koefpopitok, block);
        Adminparam adminparam = adminparamService.findById(1L);
        adminparam.setKolpopitok(kolpopitok);
        adminparam.setKoefpopitok(koefpopitok);
        adminparam.setBlock(block);
        adminparamService.save(adminparam);

        model.addAttribute("adminparam", adminparam);

        return "admin";
    }

    @GetMapping("/juraudit")
    public String juraudit(@AuthenticationPrincipal User user,
                           Model model) {

        model.addAttribute("user", user);


        return "fragment/juraudit";
    }

/*    @GetMapping("/juraudit/tables")
    public String table(@RequestParam
                        @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
                        LocalDate date1,
                        @RequestParam
                        @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
                        LocalDate date2,
                        @RequestParam String login,
                        @RequestParam String type,
                        @RequestParam String text,
                        @AuthenticationPrincipal User user,
                        Model model) {

        model.addAttribute("user", user);

        if (login.equals("")) login = null;
        if (text.equals("")) text = null;
        Iterable<Logi> logi;
        if (date1 == null || date2 == null) {
            if (login == null && type == null && text == null) {
                logi = logiService.findAll();
            } else {
                logi = logiService.findByUser(
                        login,
                        type,
                        text
                );
            }
        } else {
            logi = logiService.findByDateBetween(date1, date2, login, type, text);
        }

        //Iterable<Logi> logi = logiService.findByDateBetween(date1, date2, login, Long.valueOf(type));
        model.addAttribute("logi", logi);

        return "fragmentadmin/adminfragment :: tables";
    }*/

/*    @GetMapping("/juraudit/clear")
    public String clear(Model model) {

        logiService.clear();
        Iterable<Logi> logi = logiService.findAll();
        model.addAttribute("logi", logi);

        return "fragmentadmin/adminfragment :: tables";
    }*/


    @GetMapping("/vihod/logout")
    public void logout() {
    }

    @GetMapping("/admin/clear")
    public String admintwoclear(@RequestParam Long id,
                                Model model) {

        User logerr = userService.findById(id);
        logerr.setActive(0L);
        userService.save(logerr);

        List<User> logerrs = userService.findAll();
        model.addAttribute("logerrs", logerrs);

        return "fragmentadmin/adminfragment :: blocks";
    }


    @GetMapping("/admin/zablock")
    public String admintwozablock(@RequestParam Long id,
                                  Model model) {

        User logerr = userService.findById(id);
        logerr.setActive(1000L);
        userService.save(logerr);

        List<User> logerrs = userService.findAll();
        model.addAttribute("logerrs", logerrs);

        return "fragmentadmin/adminfragment :: blocks";
    }

}
