package com.it.rabo.lostandfound.controller.mvc;

import com.it.rabo.lostandfound.controller.ClaimController;
import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.model.ClaimsView;
import com.it.rabo.lostandfound.service.ClaimRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClaimController.class)
public class ClaimControllerMvcTest {

    @Mock
    LostFound lostFound1;

    @Mock
    LostFound lostFound2;

    @MockBean
    private ClaimRecordsService claimRecordsService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        lostFound1 = new LostFound(1L, "Laptop", 2, "Airport");
        lostFound2 = new LostFound(2L, "Laptop", 1, "Airport");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void get_all_claims_with_admin_role() throws Exception {
        Map<Long, List<ClaimsView>> claims = Map.of(1L, List.of(new ClaimsView(1L, "", 1L, 1L, "Laptop", "Airport")));
        when(claimRecordsService.getListOfClaims()).thenReturn(claims);
        mockMvc.perform(get("/claims"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string("{\"data\":{\"1\":[{\"userId\":1,\"userName\":\"\",\"quantity\":1,\"itemName\":\"Laptop\",\"place\":\"Airport\"}]},\"status\":200}"));
    }

    @Test

    void get_all_claims_without_authorization() throws Exception {
        Map<Long, List<ClaimsView>> claims = Map.of(1L, List.of(new ClaimsView(1L, "", 1L, 1L, "Laptop", "Airport")));
        when(claimRecordsService.getListOfClaims()).thenReturn(claims);
        mockMvc.perform(get("/claims"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void claim_lost_items() throws Exception {
        mockMvc.perform(post("/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content("{\"userId\":1,\"lostItemId\":1,\"quantity\":1}"))
                .andDo(print())
                .andExpect(status().isOk()).andExpect(content().string("{\"message\":\"Claim is successful\",\"status\":201}"));
    }


}
