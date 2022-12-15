package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;
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
        String sql = "drop table if exists users";
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
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
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

            String sql = "delete from users where id=?";

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();

            System.out.println("User deleted");

        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            result = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public void cleanUsersTable() {
        String sql = "truncate table users";
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
