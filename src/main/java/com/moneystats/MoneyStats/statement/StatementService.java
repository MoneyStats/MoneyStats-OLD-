package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.statement.DTO.StatementDTO;
import com.moneystats.MoneyStats.statement.DTO.StatementResponse;
import com.moneystats.MoneyStats.statement.DTO.StatementResponseDTO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.IWalletDAO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Service
public class StatementService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject UtenteCRUD userDAO;
    @Inject IWalletDAO walletDAO;
    @Inject IStatementDAO statementDAO;

    public StatementResponseDTO addStatement (Principal principal, StatementDTO statementDTO) throws StatementException{
        StatementValidator.validateStatementDTO(statementDTO);
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new StatementException(StatementException.Type.USER_NOT_FOUND);
        }
        statementDTO.setUser(utente);
        WalletEntity walletEntity = walletDAO.findById(statementDTO.getWalletEntity().getId()).orElse(null);
        if (walletEntity == null){
            LOG.error("Wallet Not Found");
            throw new StatementException(StatementException.Type.WALLET_NOT_FOUND);
        }
        statementDTO.setWalletEntity(walletEntity);
        StatementEntity statementEntity = new StatementEntity(
                statementDTO.getDate(),
                statementDTO.getValue(),
                statementDTO.getUser(),
                statementDTO.getWalletEntity());
        statementDAO.save(statementEntity);
        StatementResponseDTO response = new StatementResponseDTO(StatementResponse.Type.STATEMENT_ADDED.toString());
        return response;
    }

    public List<String> listbyDate(Principal principal) throws StatementException{
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new StatementException(StatementException.Type.USER_NOT_FOUND);
        }
        for (int i = 0; i < statementDAO.selectdistinctstatement(utente.getId()).size(); i++){
            System.out.println(statementDAO.selectdistinctstatement(utente.getId()));
            return statementDAO.selectdistinctstatement(utente.getId());
        }
        return null;
    }

    public List<StatementEntity> listByDate(Principal principal, String date) throws StatementException {
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new StatementException(StatementException.Type.USER_NOT_FOUND);
        }
        List<StatementEntity> statementList = statementDAO.findAllByUserIdAndDateOrderByWalletId(utente.getId(), date);
        if (statementList == null){
            LOG.error("Statement Not Found");
            throw new StatementException(StatementException.Type.STATEMENT_NOT_FOUND);
        }
        return statementList;
    }
    public List<String> listByWalletAndValue(Principal principal) throws StatementException {
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new StatementException(StatementException.Type.USER_NOT_FOUND);
        }
        List<String> statementsByWallet = statementDAO.findStatementByDateOrdered(utente.getId());
        if (statementsByWallet == null){
            LOG.error("Statement Not Found");
            throw new StatementException(StatementException.Type.STATEMENT_NOT_FOUND);
        }
        return statementsByWallet;
    }

}
