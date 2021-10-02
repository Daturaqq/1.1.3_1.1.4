package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Luka", "Doncic", (byte) 22);
        userService.saveUser("LeBrone", "James", (byte) 36);
        userService.saveUser("Giannis", "Antetokounmpo", (byte) 26);
        userService.saveUser("Kevin", "Durant", (byte) 32);
        userService.removeUserById(2); // проверить
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
