package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Петр", "Сидоров", (byte) 30);
        userService.saveUser("Ольга", "Коновалова", (byte) 40);
        userService.saveUser("Мария", "Кошкина", (byte) 50);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
