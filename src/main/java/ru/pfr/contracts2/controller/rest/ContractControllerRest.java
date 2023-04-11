package ru.pfr.contracts2.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pfr.contracts2.entity.contracts.contractDK.entity.ContractDk;
import ru.pfr.contracts2.service.contracts.ContractDkService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/contract/ob"})
public class ContractControllerRest {

    private final ContractDkService contractDkService;


}
