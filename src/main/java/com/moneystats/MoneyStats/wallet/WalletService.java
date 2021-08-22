package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.auth.User;
import com.moneystats.MoneyStats.auth.UtenteCRUD;
import com.moneystats.MoneyStats.category.ICategoryDAO;
import com.moneystats.MoneyStats.category.entity.CategoryEntity;
import com.moneystats.MoneyStats.statement.IStatementDAO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponse;
import com.moneystats.MoneyStats.wallet.DTO.WalletResponseDTO;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class WalletService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Inject IWalletDAO walletDAO;
    @Inject UtenteCRUD userDAO;
    @Inject ICategoryDAO categoryDAO;
    @Inject IStatementDAO statementDAO;

    public List<WalletDTO> getAll(Principal principal) throws WalletException{
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new WalletException(WalletException.Type.USER_NOT_FOUND);
        }
        List<WalletEntity> walletEntities = walletDAO.findAllByUserId(utente.getId());
        List<WalletDTO> walletDTOS = new ArrayList<>();
        WalletDTO walletDTO = null;
        for (int i = 0; i < walletEntities.size(); i++){
            walletDTO = new WalletDTO(
                    walletEntities.get(i).getName(),
                    walletEntities.get(i).getCategory(),
                    walletEntities.get(i).getUser(),
                    walletEntities.get(i).getStatementList());
            walletDTOS.add(walletDTO);
            System.out.println(walletDTO.getCategoryEntity().getName());
        }
        if (walletDTO == null){
            LOG.error("WalletDTO Null, probably for fails");
            throw new WalletException(WalletException.Type.WALLETDTO_NULL);
        }
        return walletDTOS;
    }

    public WalletResponseDTO addWalletEntity(Principal principal, Integer idCategory, WalletDTO walletDTO) throws WalletException{
        WalletValidation.validateWalletDTO(walletDTO);
        String username = principal.getName();
        User utente = (User) userDAO.findByUsername(username).orElse(null);
        if (utente == null){
            LOG.error("User Not Found");
            throw new WalletException(WalletException.Type.USER_NOT_FOUND);
        }
        walletDTO.setUser(utente);
        CategoryEntity category = categoryDAO.findById(idCategory).orElse(null);
        if (category == null){
            LOG.error("Category Not Found");
            throw new WalletException(WalletException.Type.CATEGORY_NOT_FOUND);
        }
        walletDTO.setCategoryEntity(category);
        WalletEntity walletEntity = new WalletEntity(
                walletDTO.getName(),
                walletDTO.getCategoryEntity(),
                walletDTO.getUser(),
                walletDTO.getStatementEntityList());
        walletDAO.save(walletEntity);
        return new WalletResponseDTO(WalletResponse.Type.WALLET_DELETED.toString());
    }

    public WalletResponseDTO deleteWalletEntity(Integer idWallet) throws WalletException{
        WalletEntity wallet = walletDAO.findById(idWallet).orElse(null);
        if (wallet == null){
            LOG.error("Wallet Not Found");
            throw new WalletException(WalletException.Type.WALLET_NOT_FOUND);
        }
        List<StatementEntity> statementEntities = statementDAO.findStatementByWalletId(wallet.getId());
        if (statementEntities.size() == 0){
            LOG.error("No Statement Found");
            throw new WalletException(WalletException.Type.STATEMENT_NOT_FOUND);
        }
        wallet.setStatementList(statementEntities);
        for (StatementEntity statementEntity : wallet.getStatementList()){
            statementDAO.deleteById(statementEntity.getId());
        }
        walletDAO.deleteById(idWallet);
        return new WalletResponseDTO(WalletResponse.Type.WALLET_DELETED.toString());
    }
}
