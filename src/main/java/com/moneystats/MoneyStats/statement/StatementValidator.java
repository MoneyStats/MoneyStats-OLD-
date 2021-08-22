package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.statement.DTO.StatementDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public final class StatementValidator {
    private static final Logger LOG = LoggerFactory.getLogger(StatementValidator.class);

    public static void validateStatementDTO(StatementDTO statementDTO) throws StatementException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<StatementDTO>> violations = validator.validate(statementDTO);

        if (violations.size() > 0){
            LOG.warn("Invalid Statement {}", statementDTO);
            throw new StatementException(StatementException.Type.INVALID_STATEMENT_DTO);
        }
    }
}
