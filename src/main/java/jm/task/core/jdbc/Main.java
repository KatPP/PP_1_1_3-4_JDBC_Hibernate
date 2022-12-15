package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;

public class Main {
    private static final UserService user = new UserServiceImpl();
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        Util util = new Util();
        Util.getSessionFactory();
        user.createUsersTable();
        user.saveUser("Ivan", "Ivanov", (byte) 23);
        user.saveUser("Petr", "Petrov", (byte) 32);
        user.saveUser("Sergey", "Smirnov", (byte) 20);
        user.saveUser("Sara", "Smirnova", (byte) 29);

        user.removeUserById(2);
        System.out.println(user.getAllUsers());
        user.cleanUsersTable();
        user.dropUsersTable();
    }
}
