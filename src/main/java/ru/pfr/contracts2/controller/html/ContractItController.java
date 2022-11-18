package ru.pfr.contracts2.controller.html;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.contracts2.entity.contractIT.ContractIT;
import ru.pfr.contracts2.entity.log.Logi;
import ru.pfr.contracts2.entity.user.User;
import ru.pfr.contracts2.global.ConverterDate;
import ru.pfr.contracts2.service.it.ContractItService;
import ru.pfr.contracts2.service.log.LogiService;
import ru.pfr.contracts2.service.zir.ZirServise;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = { "/contract/it"})
public class ContractItController {

    private final ContractItService contractItService;

    private final ZirServise zirServise;

    private final LogiService logiService;

    @GetMapping("/vievTable")
    public String vievTable(
            @AuthenticationPrincipal User user,
            Model model
    ){
        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы it контрактов"));
       // model.addAttribute("paginationContractName", "paginationItContract");
        model.addAttribute("findContractName", "findContractIt");
        model.addAttribute(
                "notifications",
                zirServise.getNotification(String.valueOf(user.getId_ondel_zir()))
        );
        return "fragment/it/viev :: vievTable";
    }

    @GetMapping("/getTable")  //Перелистывания
    public String getTable(
            @RequestParam(defaultValue = "") Integer param,
            @RequestParam(defaultValue = "10") Integer col,
            @AuthenticationPrincipal User user,
            Authentication authentication,
            Model model){

        //String role = User.getRole(authentication);
        String role = "IT";

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ таблицы it контрактов на странице " + param));

        Integer skip = col*(param==null?0:param-1);
        List<ContractIT> contractITs = contractItService.findAll(role)
                .stream()
                .skip(skip)
                .limit(col)
                .collect(Collectors.toList());

        model.addAttribute("contracts", contractITs);
        //model.addAttribute("paginationContractName", "paginationItContract");
        model.addAttribute("paramstart",
                skip);
        model.addAttribute("col",
                col);

        return "fragment/it/viev :: table";

    }

    @GetMapping("/findTable")
    public String findTable(
            @RequestParam(defaultValue = "") String poleFindByNomGK,
            @RequestParam(defaultValue = "") String poleFindByKontragent,
            @RequestParam(defaultValue = "") String dateGK,
            @RequestParam(defaultValue = "0") Integer idot,
            //@RequestParam(defaultValue = "30") Integer col,
            Authentication authentication,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"Find",
                "Поиск в таблице контрактов"));

        //String role = User.getRole(authentication);
        String role = "IT";

        List<ContractIT> contracts;
        if(poleFindByNomGK.equals("") && poleFindByKontragent.equals("")){
            contracts = contractItService.findAllByRole(role);
        } else {
            contracts = contractItService
                    .findByNomGK(poleFindByNomGK, poleFindByKontragent, role);
        }

        final Integer idot2 = idot;
        if(idot2!= null && !idot2.equals(0)){
            contracts = contracts
                    .stream()
                    .filter(contractIT ->
                            contractIT.getIdzirot()!=null && contractIT.getIdzirot().equals(idot2)
                    )
                    .collect(Collectors.toList());
        }

        final Date dateGK2;
        if(dateGK != null && !dateGK.equals("")){
            dateGK2 = ConverterDate.stringToDate(dateGK.trim());
            contracts = contracts
                    .stream()
                    .filter(contractIT ->
                            contractIT.getDateGK()!=null && contractIT.getDateGK().compareTo(dateGK2)==0
                    )
                    .collect(Collectors.toList());
        }


        model.addAttribute("contracts", contracts);
        model.addAttribute("paramstart", 0);

        return "fragment/it/viev :: table";
    }

    @GetMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                       Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы добавления it контракта"));

        model.addAttribute(
                "notifications",
                zirServise.getNotification(String.valueOf(user.getId_ondel_zir()))
        );

        return "fragment/it/add :: addviev";
    }

    @GetMapping("/updateViev/{id}")
    public String updateViev(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            Model model){

        logiService.save(new Logi(user.getLogin(),"View",
                "Показ страницы изменения it контракта с id = " + id));

        model.addAttribute(
                "notifications",
                zirServise.getNotification(String.valueOf(user.getId_ondel_zir()))
        );

        return "fragment/it/add :: addviev";
    }



}
