package ru.pfr.contracts2.controller.rest;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.pfr.contracts2.aop.log.valid.ValidError;
import ru.pfr.contracts2.entity.annotations.date.CustomLocalDateTimeDeserializerRuAndEnOrNull;
import ru.pfr.contracts2.entity.log.dto.ParamLog;
import ru.pfr.contracts2.entity.log.entity.Logi;
import ru.pfr.contracts2.entity.log.entity.Logi_;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.service.admin.AdminparamService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.user.UserService;

import javax.validation.Valid;
import java.time.LocalDate;


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
            @RequestParam(defaultValue = "1") Integer param,
            @RequestParam(defaultValue = "30") Integer col,
            @Valid @RequestBody ParamLog paramLog,
            @AuthenticationPrincipal User user,
            Errors errors) {


        try {
            Iterable<Logi> logi = logiService.findAll(
                    PageRequest.of(
                            0, 30,
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
