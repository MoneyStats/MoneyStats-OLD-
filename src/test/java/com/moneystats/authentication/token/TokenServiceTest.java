package com.moneystats.authentication.token;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.moneystats.authentication.AuthenticationException;
import com.moneystats.authentication.SecurityRoles;
import com.moneystats.authentication.TokenService;
import com.moneystats.authentication.DTO.AuthCredentialDTO;
import com.moneystats.authentication.DTO.TokenDTO;

@SpringBootTest(classes = TokenService.class)
public class TokenServiceTest {
	private static final String INVALID_TOKEN_JWT = "my-invalid-token-jwt";

	private static final String USERNAME = "my-username";
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	private static final String EMAIL = "email";

	@Value(value = "${jwt.secret}")
	private String secret;

	@Value(value = "${jwt.time}")
	private String expirationTime;

	@InjectMocks
	private TokenService service;

	@BeforeEach
	void checkField() {
		ReflectionTestUtils.setField(service, "expirationTime", expirationTime);
		ReflectionTestUtils.setField(service, "secret", secret);
	}

	@Test
	void generateToken_parseToken_shouldReturnSameUser() throws Exception {
		AuthCredentialDTO userDto = new AuthCredentialDTO(FIRSTNAME, LASTNAME, EMAIL, USERNAME,
				SecurityRoles.MONEYSTATS_USER_ROLE);

		TokenDTO token = service.generateToken(userDto);
		AuthCredentialDTO parseUser = service.parseToken(token);

		Assertions.assertEquals(userDto.getFirstName(), parseUser.getFirstName());
		Assertions.assertEquals(userDto.getLastName(), parseUser.getLastName());
		Assertions.assertEquals(userDto.getEmail(), parseUser.getEmail());
		Assertions.assertEquals(userDto.getUsername(), parseUser.getUsername());
		Assertions.assertEquals(userDto.getRole(), parseUser.getRole());
	}

	@Test
	void parseToken_shouldThrowUnauthorizedOnInvalidToken() throws Exception {
		TokenDTO token = new TokenDTO(INVALID_TOKEN_JWT);

		AuthenticationException expectedException = new AuthenticationException(
				AuthenticationException.Type.UNAUTHORIZED);
		AuthenticationException actualException = Assertions.assertThrows(AuthenticationException.class,
				() -> service.parseToken(token));

		Assertions.assertEquals(expectedException.getType(), actualException.getType());

	}
}
