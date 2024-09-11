package com.it.rabo.lostandfound.controller.mvc;

import com.it.rabo.lostandfound.controller.LostItemsController;
import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.service.LostItemsService;

import com.it.rabo.lostandfound.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LostItemsController.class)
public class LostItemsControllerMvcTest {

    @Mock
    LostFound lostFound1;
    @Mock
    LostFound lostFound2;
    @MockBean
    private LostItemsService lostItemsService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        lostFound1 = new LostFound(1L, "Laptop", 2, "Airport");
        lostFound2 = new LostFound(2L, "Laptop", 1, "Airport");
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testGetAllLostItemsDetails_withAdminRole() throws Exception {
        when(lostItemsService.getAllLostAndFoundDetails()).thenReturn(List.of(lostFound1, lostFound2));
        mockMvc.perform(get("/lost-items"))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    void testGetAllLostItemsDetailss_withoutAuthentication() throws Exception {
        when(lostItemsService.getAllLostAndFoundDetails()).thenReturn(List.of(lostFound1, lostFound2));
        mockMvc.perform(get("/claims"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void update_with_admin_role() throws Exception {
        File file = FileUtil.getFileFromResource("LostItems.pdf");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "LostItems.pdf", "application/pdf", new FileInputStream(file));
        mockMvc.perform(multipart("/lost-items")
                        .file(multipartFile)
                        .with(csrf())
                        .contentType("multipart/form-data"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void update_with_no_role() throws Exception {
        File file = FileUtil.getFileFromResource("LostItems.pdf");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "LostItems.pdf", "application/pdf", new FileInputStream(file));
        mockMvc.perform(multipart("/lost-items")
                        .file(multipartFile)
                        .with(csrf())
                        .contentType("multipart/form-data"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void throwExceptionIf_EmptyFileSent() throws Exception {
         mockMvc.perform(multipart("/lost-items")
                        .file(new MockMultipartFile("file", new byte[0]))
                        .with(csrf())
                        .contentType("multipart/form-data"))
                .andExpect(status().isBadRequest());

    }

}
