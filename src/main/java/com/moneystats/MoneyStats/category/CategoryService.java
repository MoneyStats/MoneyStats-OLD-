package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.category.DTO.CategoryDTO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject ICategoryDAO categoryDAO;

    public List<CategoryDTO> categoryDTOList() throws CategoryException{
        List<CategoryEntity> categoryEntities = categoryDAO.findAll();
        if (categoryEntities.size() == 0){
            LOG.error("Category Not Found");
            throw new CategoryException(CategoryException.Type.CATEGORY_ENTITIES_NOT_FOUND);
        }
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        CategoryDTO categoryDTO;
        for (CategoryEntity categoryEntity : categoryEntities){
            categoryDTO = new CategoryDTO(
                    categoryEntity.getName());
            categoryDTOS.add(categoryDTO);
        }
        if (categoryDTOS.size() == 0){
            LOG.error("Category DTO Not Found");
            throw new CategoryException(CategoryException.Type.CATEGORY_DTOS_NOT_FOUND);
        }
        return categoryDTOS;
    }
}
