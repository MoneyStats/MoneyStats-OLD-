package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.statement.DTO.StatementDTO;
import com.moneystats.MoneyStats.statement.DTO.StatementResponseDTO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/statement")
public class StatementController {

    @Inject
    StatementService statementService;

    @PostMapping("/post")
    public StatementResponseDTO addStatement(Principal principal, @RequestBody StatementDTO statement) throws StatementException{
        return statementService.addStatement(principal, statement);
    }

    @GetMapping("/datestatement")
     public List<String> listbyDate(Principal principal) throws StatementException {
        return statementService.listbyDate(principal);
    }

    @GetMapping("/statementbydate/{date}")
    public List<StatementEntity> listByDate(Principal principal, @PathVariable String date) throws StatementException {
        return statementService.listByDate(principal, date);
    }

    @GetMapping("/listStatement")
    public List<String> listByWalletAndValue(Principal principal) throws StatementException {
        return statementService.listByWalletAndValue(principal);
    }
}
