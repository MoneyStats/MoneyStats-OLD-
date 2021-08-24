package com.moneystats.MoneyStats.wallet;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneystats.MoneyStats.wallet.DTO.WalletDTO;

public class WalletValidation {
	private static final Logger LOG = LoggerFactory.getLogger(WalletValidation.class);

	public static void validateWalletDTO(WalletDTO walletDTO) throws WalletException {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<WalletDTO>> violationSet = validator.validate(walletDTO);

		if (violationSet.size() > 0) {
			LOG.warn("Invalid Statement {}", walletDTO);
			throw new WalletException(WalletException.Type.INVALID_WALLET_DTO);
		}
	}
}
