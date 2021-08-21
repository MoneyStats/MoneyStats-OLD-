package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryDAO extends JpaRepository<Category, Integer> {
}
