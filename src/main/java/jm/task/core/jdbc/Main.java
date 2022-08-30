package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        try {
            userService.createUsersTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Петр", "Сидоров", (byte) 30);
        userService.saveUser("Ольга", "Коновалова", (byte) 40);
        userService.saveUser("Мария", "Кошкина", (byte) 50);

        userService.removeUserById(1);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
