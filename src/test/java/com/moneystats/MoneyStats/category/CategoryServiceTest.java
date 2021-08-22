package com.moneystats.MoneyStats.category;

import org.junit.jupiter.api.Assertions;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.source.DTOTestObjets;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryServiceTest {

    @Mock ICategoryDAO categoryDAO;
    @InjectMocks CategoryService categoryService;

    @Test
    void categoryDTOList_testShouldReturnTheList() throws Exception{
        List<CategoryEntity> categoryEntities = DTOTestObjets.categoryEntities;
        Mockito.when(categoryDAO.findAll()).thenReturn(categoryEntities);

        List<CategoryEntity> actual = categoryDAO.findAll();
        for (int i = 0; i < actual.size(); i++){
            Assertions.assertEquals(categoryEntities.get(i).getName(), actual.get(i).getName());
        }
    }
    @Test
    void categoryDTOList_testShouldReturnCategoryEntityNotFound() throws Exception{
        CategoryException expected = new CategoryException(CategoryException.Type.CATEGORY_ENTITIES_NOT_FOUND);
        CategoryException actual = Assertions.assertThrows(CategoryException.class, () -> categoryService.categoryDTOList());

        Assertions.assertEquals(expected.getType(), actual.getType());

    }

    @Test
    void categoryDTOList_testShouldReturnCategoryDTONotFound() throws Exception{
        Mockito.when(categoryService.categoryDTOList()).thenThrow(new CategoryException(CategoryException.Type.CATEGORY_DTOS_NOT_FOUND));

        CategoryException expected = new CategoryException(CategoryException.Type.CATEGORY_DTOS_NOT_FOUND);
        CategoryException actual = (CategoryException) categoryService.categoryDTOList();

        Assertions.assertEquals(expected.getType(), actual.getType());
    }
}
