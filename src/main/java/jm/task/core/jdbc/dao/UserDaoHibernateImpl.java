package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    SessionFactory sessionFactory = new Util().getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() { // код
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)").addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Успешно// createUsersTable");
        } catch (Exception e) {
            System.out.println("Проблема// createUsersTable");
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() { // код (проверить***)
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users");
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Проблема// dropUsersTable");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {  // код
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            System.out.println("Успешно// saveUser");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Проблема// saveUser");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {  // код
        Transaction transaction;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(User.class, id);
            session.delete(user);
            transaction.commit();
            System.out.println("Успешно// removeUserById");
        } catch (Exception e) {
            System.out.println("Проблема// removeUserById");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {  // код (проверить***)
        Transaction transaction;
        List<User> userList = new ArrayList<>();
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery("from User order by name").list();
            transaction.commit();
            System.out.println("Успешно// getAllUsers");
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
            System.out.println("Успешно// cleanUsersTable");
        } catch (Exception e) {
            System.out.println("Проблема// cleanUsersTable");
            e.printStackTrace();
        }
    }
}
