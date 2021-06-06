package com.moneystats.MoneyStats.repositoryCRUD;

import com.moneystats.MoneyStats.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStatementCRUD extends JpaRepository<Statement, Integer> {
    List<Statement> findStatementByUserId(int userId);

    @Query(value = "select distinct statements.date from Statement statements where statements.user.id = :userId")
    List<String> selectdistinctstatement(int userId);

    @Query(value = "select '*' from Statement statements where statements.date = :date and statements.user.id = :userId")
    List<Statement> findStatementByDate(@Param("date") String date, int userId);
}