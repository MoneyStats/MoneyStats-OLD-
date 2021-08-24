package com.moneystats.MoneyStats.category;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import com.moneystats.MoneyStats.source.DTOTestObjets;

@AutoConfigureMockMvc
@SpringBootTest(properties = "spring.main.lazy-initialization=true", classes = CategoryController.class)
public class CategoryControllerTest {

	@MockBean
	private CategoryService categoryService;
	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void testGetAllCategory_OK() throws Exception {
		List<CategoryDTO> categoryDTOS = DTOTestObjets.categoryDTOList;
		String categoryAsString = objectMapper.writeValueAsString(categoryDTOS);
		Mockito.when(categoryService.categoryDTOList()).thenReturn(categoryDTOS);

		mockMvc.perform(MockMvcRequestBuilders.get("/category/list")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(categoryAsString));
	}

	@Test
	public void testGetAllCategory_shouldBeMappedOnCategoryNotFound() throws Exception {
		Mockito.when(categoryService.categoryDTOList())
				.thenThrow(new CategoryException(CategoryException.Type.CATEGORY_NOT_FOUND));

		mockMvc.perform(MockMvcRequestBuilders.get("/category/list")).andExpect(MockMvcResultMatchers.status().is(404));
	}
}
