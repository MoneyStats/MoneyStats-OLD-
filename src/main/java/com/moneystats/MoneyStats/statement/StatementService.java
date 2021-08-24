package com.moneystats.MoneyStats.statement;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moneystats.MoneyStats.statement.DTO.StatementDTO;
import com.moneystats.MoneyStats.statement.DTO.StatementResponse;
import com.moneystats.MoneyStats.statement.DTO.StatementResponseDTO;
import com.moneystats.MoneyStats.statement.entity.StatementEntity;
import com.moneystats.MoneyStats.wallet.IWalletDAO;
import com.moneystats.MoneyStats.wallet.WalletException;
import com.moneystats.MoneyStats.wallet.entity.WalletEntity;
import com.moneystats.authentication.AuthCredentialDAO;
import com.moneystats.authentication.AuthenticationException;
import com.moneystats.authentication.TokenService;
import com.moneystats.authentication.TokenValidation;
import com.moneystats.authentication.DTO.AuthCredentialDTO;
import com.moneystats.authentication.DTO.AuthCredentialInputDTO;
import com.moneystats.authentication.DTO.TokenDTO;
import com.moneystats.authentication.entity.AuthCredentialEntity;

@Service
public class StatementService {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());

	@Inject
	private IWalletDAO walletDAO;
	@Inject
	private IStatementDAO statementDAO;
	@Inject
	private AuthCredentialDAO authCredentialDAO;
	@Inject
	private TokenService tokenService;

	public StatementResponseDTO addStatement(TokenDTO tokenDTO, StatementDTO statementDTO)
			throws StatementException, WalletException, AuthenticationException {
		StatementValidator.validateStatementDTO(statementDTO);
		AuthCredentialEntity utente = validateAndCreate(tokenDTO);
		statementDTO.setUser(utente);
		WalletEntity walletEntity = walletDAO.findById(statementDTO.getWalletEntity().getId()).orElse(null);
		if (walletEntity == null) {
			LOG.error("Wallet Not Found");
			throw new StatementException(StatementException.Type.WALLET_NOT_FOUND);
		}
		statementDTO.setWalletEntity(walletEntity);
		StatementEntity statementEntity = new StatementEntity(statementDTO.getDate(), statementDTO.getValue(),
				statementDTO.getUser(), statementDTO.getWalletEntity());
		statementDAO.save(statementEntity);
		StatementResponseDTO response = new StatementResponseDTO(StatementResponse.Type.STATEMENT_ADDED.toString());
		return response;
	}

	public List<String> listbyDate(TokenDTO tokenDTO)
			throws StatementException, WalletException, AuthenticationException {
		AuthCredentialEntity utente = validateAndCreate(tokenDTO);

		for (int i = 0; i < statementDAO.selectdistinctstatement(utente.getId()).size(); i++) {
			System.out.println(statementDAO.selectdistinctstatement(utente.getId()));
			return statementDAO.selectdistinctstatement(utente.getId());
		}
		return null;
	}

	public List<StatementEntity> listByDate(TokenDTO tokenDTO, String date)
			throws StatementException, WalletException, AuthenticationException {
		AuthCredentialEntity utente = validateAndCreate(tokenDTO);

		List<StatementEntity> statementList = statementDAO.findAllByUserIdAndDateOrderByWalletId(utente.getId(), date);
		if (statementList == null) {
			LOG.error("Statement Not Found");
			throw new StatementException(StatementException.Type.STATEMENT_NOT_FOUND);
		}
		return statementList;
	}

	public List<String> listByWalletAndValue(TokenDTO tokenDTO)
			throws StatementException, WalletException, AuthenticationException {
		AuthCredentialEntity utente = validateAndCreate(tokenDTO);

		List<String> statementsByWallet = statementDAO.findStatementByDateOrdered(utente.getId());
		if (statementsByWallet == null) {
			LOG.error("Statement Not Found");
			throw new StatementException(StatementException.Type.STATEMENT_NOT_FOUND);
		}
		return statementsByWallet;
	}

	private AuthCredentialEntity validateAndCreate(TokenDTO tokenDTO) throws AuthenticationException, WalletException {
		TokenValidation.validateTokenDTO(tokenDTO);
		if (tokenDTO.getAccessToken().equalsIgnoreCase("")) {
			throw new AuthenticationException(AuthenticationException.Type.TOKEN_REQUIRED);
		}
		AuthCredentialDTO authCredentialDTO = tokenService.parseToken(tokenDTO);
		AuthCredentialInputDTO authCredentialInputDTO = new AuthCredentialInputDTO(authCredentialDTO.getUsername(),
				authCredentialDTO.getPassword());
		AuthCredentialEntity utente = authCredentialDAO.getCredential(authCredentialInputDTO);
		if (utente == null) {
			LOG.error("User Not Found");
			throw new WalletException(WalletException.Type.USER_NOT_FOUND);
		}
		return utente;
	}
}
