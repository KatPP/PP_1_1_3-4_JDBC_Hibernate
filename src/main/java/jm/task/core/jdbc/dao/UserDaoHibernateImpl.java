package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "create table if not exists users" +
                    "(id bigint auto_increment, " +
                    "name varchar(45), " +
                    "lastName varchar(45), " +
                    "age tinyint, primary key (id))";

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Table created");

        } catch (HibernateException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE if EXISTS users";
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("Table deleted");

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            String sql = "INSERT INTO users (name, lastName, age) VALUES ('" + name + "', '" +
                    lastName + "', " + age + ")";
            session.createSQLQuery(sql)
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();

            System.out.println("User saved");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "DELETE FROM users WHERE id = " + id;

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("User deleted");

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List userList = null;
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "SELECT * FROM users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            userList = query.getResultList();
            for (Object user : userList) {
                System.out.print(user.toString());
            }
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = "TRUNCATE TABLE users";
            session.createSQLQuery(sql)
                    .addEntity(User.class)
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}

