package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDAO extends JpaRepository<CategoryEntity, Integer> {
}
