package com.JAVA.DAO;

import com.JAVA.Beans.User;
import com.JAVA.utils.DAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    private final Connection connection;

    public UserDAOImpl(DAOFactory daoFactory) throws SQLException {
        this.connection = daoFactory.getConnection();
    }

    @Override
    public User findUserByLoginAndPassword(String email, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(
                        rs.getLong("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("telephone"),
                        rs.getLong("type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
