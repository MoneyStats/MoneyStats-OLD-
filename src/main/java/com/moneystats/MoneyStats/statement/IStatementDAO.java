package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.model.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author giova
 */
@Repository
public interface IStatementDAO extends JpaRepository<Statement, Integer>{

    List<Statement> findStatementByUserId(int userId);

    @Query(value = "select distinct statements.date from Statement statements where statements.user.id = :userId")
    List<String> selectdistinctstatement(int userId);

    List<Statement> findAllByUserIdAndDateOrderByWalletId(int userId, String date);


    @Query(value = "select statements.date, group_concat(statements.wallet.id) as wallet, group_concat(statements.value) from Statement statements where statements.user.id = :userId group by statements.date")
    List<String> findStatementByDateOrdered(int userId);

    List<Statement> findStatementByWalletId(int id);
}