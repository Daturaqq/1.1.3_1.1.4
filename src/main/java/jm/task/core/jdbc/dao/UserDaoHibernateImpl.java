package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery("create table if not exists Users (ID bigint primary key auto_increment,Name varchar(99) not null,LastName varchar(99),Age tinyint)")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery("drop table if exists Users")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE User WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        List<User> users = null;
        try {
            users = session.createQuery("FROM User").list();
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionHibernate();
        Transaction transaction = session.beginTransaction();
        try {
            session.createSQLQuery("truncate table Users").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            try {
                transaction.rollback();
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }
}
