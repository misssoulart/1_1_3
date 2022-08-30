package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Соединение с БД не установлено");
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection socket = DriverManager.getConnection("jdbc:mysql://localhost:3306/user1.1.3?useSSL=false&allowPublicKeyRetrieval=true", "stack", "12345");
        return socket;
    }

    public static Util getInstance() {
        return null;
    }
}

    /*
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    public static void main(String[] args) {
        Connection connection;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединение с БД установлено");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Соединение с БД не установлено");
        }
    }
} */