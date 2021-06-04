package com.moneystats.MoneyStats.repositoryCRUD;

import com.moneystats.MoneyStats.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryCRUD extends JpaRepository<Category, Integer> {
}
