package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.contracts2.entity.user.ROLE_ENUM;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = {"/", "/contract"})
public class AuthController {

    @RequestMapping
    public String mains(
            Authentication authentication
    ) {


        boolean admin = false;
        for (GrantedAuthority g :
                authentication.getAuthorities()) {
            //if(g.getAuthority().equals(ROLE_ENUM.ROLE_UPDATE.getString())){}
            if (g.getAuthority().equals(ROLE_ENUM.ROLE_ADMIN.getString())) admin = true;
        }

        if (admin) return "redirect:/contract/admin/admin";

        else return "redirect:/contract/main";



/*        if (user.getRayon().getKod().equals("000"))
            return "redirect:/contract/main";
        else if (user.getRayon().getKod().equals("999"))
            return "redirect:/contract/admin";
        else if (user.getRayon().getKod().equals("001") ||
                user.getRayon().getKod().equals("002") ||
                user.getRayon().getKod().equals("003") ||
                user.getRayon().getKod().equals("004") ||
                user.getRayon().getKod().equals("005") ||
                user.getRayon().getKod().equals("006") ||
                user.getRayon().getKod().equals("007") ||
                user.getRayon().getKod().equals("009") ||
                user.getRayon().getKod().equals("013") ||
                user.getRayon().getKod().equals("014") ||
                user.getRayon().getKod().equals("015") ||
                user.getRayon().getKod().equals("016") ||
                user.getRayon().getKod().equals("017") ||
                user.getRayon().getKod().equals("018") ||
                user.getRayon().getKod().equals("019") ||
                user.getRayon().getKod().equals("020") ||
                user.getRayon().getKod().equals("021") ||
                user.getRayon().getKod().equals("022") ||
                user.getRayon().getKod().equals("023") ||
                user.getRayon().getKod().equals("024") ||
                user.getRayon().getKod().equals("025") ||
                user.getRayon().getKod().equals("026") ||
                user.getRayon().getKod().equals("027"))
            return "redirect:/contract/main";
        return "/contract/error";*/
    }
}
