package com.moneystats.MoneyStats.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @MockBean private CategoryService categoryService;
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Test
    public void testGetAllCategory_OK() throws Exception {
        List<CategoryDTO> categoryDTOS = DTOTestObjets.categoryDTOList;
        String categoryAsString = objectMapper.writeValueAsString(categoryDTOS);
        Mockito.when(categoryService.categoryDTOList()).thenReturn(categoryDTOS);

        mockMvc.perform(MockMvcRequestBuilders.get("/category/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(categoryAsString));
    }
}
