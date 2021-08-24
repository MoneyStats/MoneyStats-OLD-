package com.moneystats.authentication;

import com.moneystats.authentication.DTO.AuthCredentialDTO;
import com.moneystats.authentication.DTO.AuthCredentialInputDTO;
import com.moneystats.authentication.entity.AuthCredentialEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthCredentialDAO  {

        private final Logger LOG = LoggerFactory.getLogger(this.getClass());

        private static final String SELECT_FROM_USERS_WHERE_ROLE = "SELECT * FROM users WHERE role = 'USER'";
        private static final String SELECT_FROM_USERS = "SELECT * FROM users WHERE username = ?";
        private static final String INSERT_INTO_USERS = "INSERT INTO users (firstName, lastName, dateOfBirth, email, username, password, role) VALUES (?, ?, ?, ?, ?, ?, 'USER')";
        private String dbAddress;
        private String username;
        private String password;

        public AuthCredentialDAO(@Value("${spring.datasource.url}") String dbAddress, @Value("${spring.datasource.username}") String username,
                                 @Value("${spring.datasource.password}") String password) {
            super();
            this.dbAddress = dbAddress;
            this.username = username;
            this.password = password;
        }

        public void insertUserCredential(AuthCredentialDTO user) throws AuthenticationException {
            try {
                Connection connection = DriverManager.getConnection(dbAddress, username, password);
                String sqlCommand = INSERT_INTO_USERS;
                PreparedStatement pstm = connection.prepareStatement(sqlCommand);
                pstm.setString(1, user.getFirstName());
                pstm.setString(2, user.getLastName());
                pstm.setString(3, user.getDateOfBirth());
                pstm.setString(4, user.getEmail());
                pstm.setString(5, user.getUsername());
                pstm.setString(6, user.getPassword());
                pstm.execute();
            } catch (SQLException e) {
                LOG.error("Database Error in Insert");
                throw new AuthenticationException(AuthenticationException.Type.DATABASE_ERROR);
            }
        }

        public AuthCredentialEntity getCredential(AuthCredentialInputDTO user) throws AuthenticationException {
            AuthCredentialEntity userCredential = null;
            try {
                Connection connection = DriverManager.getConnection(dbAddress, username, password);
                String sqlCommand = SELECT_FROM_USERS;
                PreparedStatement pstm = connection.prepareStatement(sqlCommand);
                pstm.setString(1, user.getUsername());
                ResultSet rs = pstm.executeQuery();
                if (rs.next()) {
                    userCredential = new AuthCredentialEntity(
                            rs.getLong("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("dateOfBirth"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"));
                }
            } catch (SQLException e) {
                LOG.error("Database Error in getCredential");
                throw new AuthenticationException(AuthenticationException.Type.DATABASE_ERROR);
            }
            return userCredential;

        }

        public List<AuthCredentialEntity> getUsers() throws AuthenticationException {
            List<AuthCredentialEntity> listUser = new ArrayList<AuthCredentialEntity>();
            try {
                Connection connection = DriverManager.getConnection(dbAddress, username, password);
                String sqlCommand = SELECT_FROM_USERS_WHERE_ROLE;
                PreparedStatement pstm = connection.prepareStatement(sqlCommand);
                ResultSet rs = pstm.executeQuery();

                while (rs.next()) {
                    listUser.add(new AuthCredentialEntity(
                            rs.getLong("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("dateOfBirth"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role")));
                }
            } catch (SQLException e) {
                LOG.error("Database Error in getAllUser");
                throw new AuthenticationException(AuthenticationException.Type.DATABASE_ERROR);
            }
            return listUser;
        }
}
