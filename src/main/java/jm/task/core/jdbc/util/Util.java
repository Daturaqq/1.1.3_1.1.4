package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/dbone";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // JDBC
    private static final Connection connection = createConnectionJDBC();

    private static Connection createConnectionJDBC() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static Connection getConnectionJDBC() {
        return connection;
    }


    // Hibernate
    private static final SessionFactory sessionFactory = createSessionFactory();

    public static SessionFactory createSessionFactory() {
        SessionFactory sessionFactory = null;
        try {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.driver_class", DRIVER)
                .setProperty("hibernate.connection.url", URL)
                .setProperty("hibernate.connection.username", USERNAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hbm2ddl_auto", "update")
                .setProperty("hibernate.current_session_context_class", "thread")
                .addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }
    public static Session getSessionHibernate() {
        return sessionFactory.openSession();
    }
}
