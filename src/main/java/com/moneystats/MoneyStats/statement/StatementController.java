package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.statement.DTO.StatementDTO;
import com.moneystats.MoneyStats.statement.DTO.StatementResponseDTO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.WalletException;
import com.moneystats.authentication.AuthenticationException;
import com.moneystats.authentication.DTO.TokenDTO;
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
    public StatementResponseDTO addStatement(@RequestHeader(value = "Authorization") String jwt, @RequestBody StatementDTO statement) throws StatementException, WalletException, AuthenticationException {
        TokenDTO tokenDTO = new TokenDTO(jwt);
        return statementService.addStatement(tokenDTO, statement);
    }

    @GetMapping("/datestatement")
     public List<String> listbyDate(@RequestHeader(value = "Authorization") String jwt) throws StatementException, WalletException, AuthenticationException {
        TokenDTO tokenDTO = new TokenDTO(jwt);
        return statementService.listbyDate(tokenDTO);
    }

    @GetMapping("/statementbydate/{date}")
    public List<StatementEntity> listByDate(@RequestHeader(value = "Authorization") String jwt, @PathVariable String date) throws StatementException, WalletException, AuthenticationException {
        TokenDTO tokenDTO = new TokenDTO(jwt);
        return statementService.listByDate(tokenDTO, date);
    }

    @GetMapping("/listStatement")
    public List<String> listByWalletAndValue(@RequestHeader(value = "Authorization") String jwt) throws StatementException, WalletException, AuthenticationException {
        TokenDTO tokenDTO = new TokenDTO(jwt);
        return statementService.listByWalletAndValue(tokenDTO);
    }
}
