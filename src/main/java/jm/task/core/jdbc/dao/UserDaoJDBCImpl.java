package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnectionJDBC().createStatement()) {
            statement.execute("create table if not exists Users (ID bigint primary key auto_increment,Name varchar(99) not null,LastName varchar(99),Age tinyint)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnectionJDBC().createStatement()) {
            statement.execute("drop table if exists Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnectionJDBC()
                .prepareStatement("insert into Users (Name, LastName, Age) values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnectionJDBC()
                .prepareStatement("DELETE FROM userTable WHERE ID = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = Util.getConnectionJDBC().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from Users");
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString("Name"),
                        resultSet.getString("LastName"),
                        resultSet.getByte("Age"));
                user.setId(resultSet.getLong("ID"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnectionJDBC().createStatement()) {
            statement.execute("truncate table Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
