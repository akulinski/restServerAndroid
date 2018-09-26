package hibernateManagers;

import hibernateManagers.users.Photo;
import hibernateManagers.users.Stalker;
import hibernateManagers.users.Victim;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Hibernate class used for data manipulations
 * referring Victim
 */
public class StalkerManager {

    private static SessionFactory factory;

    public StalkerManager() {

        try {
            factory = new Configuration().configure("hibernate.cfg.xml").addPackage("hibernateManagers/users").addAnnotatedClass(Stalker.class).addAnnotatedClass(Victim.class).addAnnotatedClass(Photo.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    /**
     * Allows to add Stalker object ot DB
     *
     * @param username name of Stalker
     * @param password password of Stalker
     * @param email    email of Stalker
     * @return Id of added Stalker
     */
    public Integer addStalker(String username, String password, String email) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer stalkerId = null;

        try {
            tx = session.beginTransaction();
            Stalker stalker = new Stalker(username, password, email, new Date(), new Date());
            stalkerId = (Integer) session.save(stalker);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
        }

        return stalkerId;
    }

    /**
     * Returns object of class Stalker with
     *
     * @param id
     * @return
     */
    public Stalker getStalker(int id) {

        Session session = factory.openSession();

        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "FROM Stalker S WHERE S.id = :id ";

            Query query = session.createQuery(hql);

            query.setParameter("id", id);

            return (Stalker) query.list().get(0);
        } finally {
            session.close();
        }

    }

    /**
     * Saves Stalker object to db
     *
     * @param stalker Stalker object
     * @return id of Stalker from DB
     */
    public Integer addStalkerFromObject(Stalker stalker) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer stalkerId = null;

        try {
            tx = session.beginTransaction();
            stalkerId = (Integer) session.save(stalker);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (tx != null) {
                tx.rollback();
            }
            session.close();
        }

        return stalkerId;
    }

    /**
     * @return List of all stalkers
     */
    public LinkedList<Stalker> listStalkers() {
        Session session = factory.openSession();
        Transaction tx = null;
        LinkedList<Stalker> stalkersList = new LinkedList<>();
        try {

            tx = session.beginTransaction();

            List stalkers = session.createQuery("FROM Stalker").list();

            for (Iterator iterator = stalkers.iterator(); iterator.hasNext(); ) {
                Stalker stalker = (Stalker) iterator.next();
                ;
                stalkersList.add(stalker);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return stalkersList;
    }

    /**
     * Function used for logging in
     *
     * @param username username of Stalker
     * @param password password of Stalker
     * @return boolean indicating whether logging in was successful
     */
    public boolean login(String username, String password) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            String hql = "FROM Stalker S WHERE S.username = :username AND S.password = :password";

            Query query = session.createQuery(hql);

            query.setParameter("username", username);
            query.setParameter("password", password);

            List result = query.list();

            if (result.size() > 0) {
                return true;
            }

        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }
}
