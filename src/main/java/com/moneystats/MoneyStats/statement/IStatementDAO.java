package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author giova
 */
@Repository
public interface IStatementDAO extends JpaRepository<StatementEntity, Integer>{

    List<StatementEntity> findStatementByUserId(Integer userId);

    @Query(value = "select distinct statements.date from StatementEntity statements where statements.user.id = :userId")
    List<String> selectdistinctstatement(Integer userId);

    List<StatementEntity> findAllByUserIdAndDateOrderByWalletId(Integer userId, String date);


    @Query(value = "select statements.date, group_concat(statements.wallet.id) as wallet, group_concat(statements.value) from StatementEntity statements " +
            "where statements.user.id = :userId group by statements.date")
    List<String> findStatementByDateOrdered(Integer userId);

    List<StatementEntity> findStatementByWalletId(Integer id);
}