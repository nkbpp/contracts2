package ru.pfr.contracts2.controller.html;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.pfr.contracts2.config.sec.WithMockCustomUser;
import ru.pfr.contracts2.service.contracts.VidObespService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@SpringBootTest
@AutoConfigureMockMvc//запускается полный контекст приложения Spring, но без сервера
//@WebMvcTest(controllers = {VidObespController.class}) // создаст только бин контроллера, а репозиторий создавать не будет.
class VidObespControllerTest {

    @MockBean
    private VidObespService vidObespService;

    @Autowired
    private MockMvc mvc;

    @Test
    //@WithMockUser(value = "testUser", roles = {"UPDATE_IT", "READ_IT"})
    @WithMockCustomUser
    void vidObespSpisokViev() throws Exception {
        Mockito.when(vidObespService.findAll())
                .thenReturn(List.of());

        mvc.perform(get("/contract/main/vidObespSpisokViev"))
                .andExpect(status().isOk());
    }

}