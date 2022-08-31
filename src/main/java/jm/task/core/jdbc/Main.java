package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private final static UserService userService = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();

        /*
        userDao.saveUser("Иван", "Иванов", (byte) 20);
        userDao.saveUser("Петр", "Сидоров", (byte) 30);
        userDao.saveUser("Ольга", "Коновалова", (byte) 40);
        userDao.saveUser("Мария", "Кошкина", (byte) 50); */

    }
}
