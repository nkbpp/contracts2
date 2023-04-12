package ru.pfr.contracts2.controller.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.pfr.contracts2.config.sec.WithMockCustomUser;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.dto.StatusGk;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.entity.ContractIT;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.entity.Months;
import ru.pfr.contracts2.entity.contractOtdel.contractIT.mapper.ContractItMapper;
import ru.pfr.contracts2.entity.contractOtdel.contractDop.mapper.DopDocumentsMapper;
import ru.pfr.contracts2.service.it.ContractItService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.apache.http.entity.ContentType.DEFAULT_BINARY;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc//запускается полный контекст приложения Spring, но без сервера
class ContractItControllerRestUnitTest {

    @Autowired
    private ContractItMapper contractItMapper;

    @Autowired
    private DopDocumentsMapper documentsMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ContractItService contractItService;
    private final ContractIT oldContractIT;

    private final MockMultipartFile A_FILE1 = new MockMultipartFile(
            "document",
            null,
            DEFAULT_BINARY.toString(),
            "Employee Record".getBytes());

    private static final MockMultipartFile A_FILE2 = new MockMultipartFile(
            "document2",
            null,
            DEFAULT_BINARY.toString(),
            "Employee Record2".getBytes());

    {
        oldContractIT = ContractIT.builder()
                .id(1L)
                .nomGK("номер")
                .kontragent("контрагент")
                .dateGK(LocalDateTime.of(LocalDate.of(1993, 8, 13), LocalTime.of(10, 20)))
                .dateGKs(LocalDateTime.of(LocalDate.of(1993, 12, 13), LocalTime.of(10, 20)))
                .dateGKpo(LocalDateTime.of(LocalDate.of(2018, 7, 13), LocalTime.of(16, 15)))
                .statusGK(StatusGk.EXECUTED)
                .sum(100D)
                .months(Months.builder()
                        .month1(1D)
                        .month2(2D)
                        .month3(3D)
                        .month4(4D)
                        .month5(5D)
                        .month6(6D)
                        .month7(7D)
                        .month8(8D)
                        .month9(9D)
                        .month10(10D)
                        .month11(11D)
                        .month12(12D)
                        .build()
                )


                .idzirot(1)
                .nameot("Господин Работник")
                //.naturalIndicators(new ArrayList<>())//только для отдела AXO тут не нужен

                .build();
    }

    @Test
    @WithMockCustomUser(login = "IT", roles = {"ROLE_UPDATE_IT", "ROLE_READ_IT"})
    void delete() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/contract/dop/999999")
                                .with(csrf())//Чтобы указать допустимый токен CSRF в качестве параметра запроса
                        //.with(csrf().asHeader())//включить CSRF-токен в заголовок
                        //.with(csrf().useInvalidToken()) //предоставление недопустимого токена CSRF
                )
                .andExpect(status().isOk());
    }


    @Test
    @WithMockCustomUser(login = "IT", roles = {"ROLE_UPDATE_IT", "ROLE_READ_IT"})
    void update() throws Exception {
        MockMultipartFile contractJson = new MockMultipartFile(
                "contract",
                null,
                "application/json",
                """ 
                            {
                                "nomGK":"Наименование",
                                "dateGK":"01.02.2023",
                                "kontragent":"Контрагент",
                                "dateGKs":"02.02.2023",
                                "dateGKpo":"03.02.2023",
                                "sum":"1000",
                                "january":"10",
                                "february":"20",
                                "march":"30",
                                "april":"40",
                                "may":"50",
                                "june":"60",
                                "july":"70",
                                "august":"80",
                                "september":"90",
                                "october":"100",
                                "november":"110",
                                "december":"120",
                                "statusGK":"Исполнен",
                                "idzirot":"1997"
                            }
                        """.getBytes()
        );


        Mockito.when(contractItService.findById(Mockito.any()))
                .thenReturn(oldContractIT);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart("/contract/dop");

        builder.with(
                new RequestPostProcessor() {
                    @Override
                    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        request.setMethod("PUT");
                        return request;
                    }
                }
        );

        mockMvc.perform(builder
                        .file(A_FILE1)
                        .file(contractJson)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }


}