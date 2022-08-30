package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

// Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection;

    static {
        try {
            connection = Util.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }
    // Создание таблицы для User(ов) – не должно приводить к исключению, если такая таблица уже существует
    @Override
    public void createUsersTable() { //работает
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление таблицы User(ов) – не должно приводить к исключению, если таблицы не существует
    @Override
    public void dropUsersTable() { //работает
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Добавление User в таблицу
    @Override
    public void saveUser(String name, String lastName, byte age) { // НЕ работает
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление User из таблицы ( по id )
    @Override
    public void removeUserById(long id) {  // работает
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Получение всех User(ов) из таблицы
    @Override
    public List<User> getAllUsers() { // НЕ работает *
        List<User> userList = new ArrayList<>();

        try (Statement statement = getConnection().createStatement())
        {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

       /* List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    } */

    // Очистка содержания таблицы
    @Override
    public void cleanUsersTable() {  //НЕ работает
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}









/* package jm.task.core.jdbc.dao;

import com.mysql.cj.util.Util;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

//  Обработка всех исключений, связанных с работой с базой данных должна находиться в dao (дано)
public class UserDaoJDBCImpl extends Util implements UserDao {

    public UserDaoJDBCImpl() { // дано, не трогала !
    }

    public void createUsersTable() throws SQLException { // создаем таблицу
        String stringCommand = "CREATE TABLE IF NOT EXISTS users" +
                "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)";

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(stringCommand);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            //INT, VARCHAR(255) - типы данных: числовой и строковый соответственно.
            //CREATE TABLE IF NOT EXISTS - базовый синтаксис ля создания таблицы, которой еще нет
            //id BIGINT PRIMARY KEY AUTO_INCREMENT - для каждой новой записи для поля id будет генерироваться значение на единицу больше ранее созданного для этой таблицы

        }

    public void dropUsersTable() { // удаляем таблицу
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) { // добавляем В таблицу
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setString(1, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setString(2, lastName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.setByte(3, age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) { // удалям данные из таблицы по id
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getConnection().prepareStatement("DELETE FROM users WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        };
        try {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getAllUsers() { // получаем ВСЕ данные из таблицы
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM users")){
            while(resultSet.next()) {
                User user = new User (resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void cleanUsersTable() {
        Statement statement = null;
        try {
            statement = getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Connection connection = new Util().getConnection();
}
*/