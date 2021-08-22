package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class CategoryControllerTest {

    @Mock private CategoryService categoryService;
    @InjectMocks private CategoryController categoryController;
    @Mock private ICategoryDAO categoryDAO;

    @Test
    public void testGetAllCategory_OK() throws CategoryException{
        List<CategoryDTO> categoryDTOS = DTOTestObjets.categoryDTOList;
        List<CategoryEntity> categoryEntities = DTOTestObjets.categoryEntities;
        CategoryService categoryService1 = new CategoryService();
        CategoryController categoryController1 = new CategoryController();
        Mockito.when(categoryService1.categoryDTOList()).thenReturn(categoryDTOS);
        Mockito.when(categoryDAO.findAll()).thenReturn(categoryEntities);
    }
}
