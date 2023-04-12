package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.contracts2.service.contracts.ContractDkService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/ob"})
public class ContractControllerRest {

    private final ContractDkService contractDkService;


}
