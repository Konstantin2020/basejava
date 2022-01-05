package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T executeSqlQuery(String query, BlockCode<T> blockCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return blockCode.execute(ps);
        } catch (SQLException e) {
//          System.out.println(e.getSQLState()); при наличии uuid уже в БД вод ошибки 23505
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(e.getMessage());
            }
            throw new StorageException(e);
        }
    }

    public interface BlockCode<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }
}


