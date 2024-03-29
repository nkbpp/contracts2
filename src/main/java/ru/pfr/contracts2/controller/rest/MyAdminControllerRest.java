package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.aop.log.valid.ValidError;
import ru.pfr.contracts2.entity.log.dto.ParamLog;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.entity.log.entity.Logi_;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.services.admin.AdminparamService;
import ru.pfr.contracts2.services.log.LogiService;
import ru.pfr.contracts2.services.user.UserService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/admin"})
public class MyAdminControllerRest {
    private final UserService userService;
    private final LogiService logiService;
    private final AdminparamService adminparamService;

    @GetMapping("/juraudit/clear")
    public ResponseEntity<?> clear() {
        try {
            logiService.clear();
            return ResponseEntity.ok("Логи очищены!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось почистить логи!");
        }
    }

    @ValidError
    @PostMapping("/juraudit/tables")
    public ResponseEntity<?> table(
            @RequestParam(defaultValue = "0") Integer param,
            @RequestParam(defaultValue = "30") Integer col,
            @Valid @RequestBody ParamLog paramLog,
            @AuthenticationPrincipal User user,
            Errors errors) {

        if (errors.hasErrors()) {
            System.out.println("sdf");
        }

        try {
            Iterable<Logi> logi = logiService.findAll(
                    PageRequest.of(
                            param, col,
                            Sort.by(Logi_.DATE).descending()
                    )
            );
            return ResponseEntity.ok(logi);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Не удалось почистить логи!");
        }

    }



    /*@GetMapping("/admin/zablock")
    public String admintwozablock(@RequestParam Long id,
                                  Model model) {

        User logerr = userService.findById(id);
        logerr.setActive(1000L);
        userService.save(logerr);

        List<User> logerrs = userService.findAll();
        model.addAttribute("logerrs", logerrs);

        return "fragmentadmin/adminfragment :: blocks";
    }*/

}
