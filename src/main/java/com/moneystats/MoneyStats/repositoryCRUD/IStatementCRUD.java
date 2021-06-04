package com.moneystats.MoneyStats.repositoryCRUD;

import com.moneystats.MoneyStats.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatementCRUD extends JpaRepository<Statement, Integer> {
    List<Statement> findStatementByUserId(int userId);
}
