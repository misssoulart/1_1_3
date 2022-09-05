package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;
// Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
public class UserDaoJDBCImpl implements UserDao {

     /*private static final Connection connection;

    static {
        try {
            connection = Util.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } */

    public UserDaoJDBCImpl() {
    }

    // Создание таблицы для User'ов
    public void createUsersTable() throws SQLException { //работает
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE UsersNew " +
                    "(Id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40), lastname VARCHAR(40),age INT(3))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // Удаление таблицы User'ов
    public void dropUsersTable() throws SQLException { //работает
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE UsersNew");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        // Добавление User'ов в таблицу
    public void saveUser(String name, String lastName, byte age) throws SQLException { //  работает
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usersnew (name, lastname, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Добавлен юзер - " + name + " в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Удаление User'ов из таблицы ( по id )
    public void removeUserById(long id) throws SQLException {  // работает

            try (Connection connection = getConnection()) {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM usersnew where Id = ?");
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

            // Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ID, NAME, LASTNAME, AGE FROM USERSNEW");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // Очистка содержания таблицы
    public void cleanUsersTable() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE usersnew");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
