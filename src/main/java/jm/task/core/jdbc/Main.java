package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoHibernateImpl();

        userDao.saveUser("Ivan", "Ivanov", (byte) 23);
        userDao.saveUser("Petr", "Petrov", (byte) 32);
        userDao.saveUser("Sergey", "Smirnov", (byte) 20);
        userDao.saveUser("Sara", "Smirnova", (byte) 29);

        userDao.removeUserById(2);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
