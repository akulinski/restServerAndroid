package hibernateManagers;

import org.hibernate.query.Query;
import users.Cordinates;
import users.Photo;
import users.Stalker;
import users.Victim;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import java.util.LinkedList;
import java.util.List;


/**
 * Hibernate class used for data manipulations
 * referring Victim
 */
public class VictimManager {

    private static SessionFactory factory;

    public VictimManager() {
        try {
            factory = new AnnotationConfiguration().configure("hibernate.cfg.xml").addPackage("users").addAnnotatedClass(Stalker.class).addAnnotatedClass(Victim.class).addAnnotatedClass(Photo.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    /**
     * @param victim Victim that has to be added to DB
     * @return Id of added victim
     */
    public Integer addVictim(Victim victim) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer victimId = null;

        try {
            tx = session.beginTransaction();
            victimId = (Integer) session.save(victim);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return victimId;
    }

    /**
     *
     * @param id Id of victim
     * @return Victim with ID = id
     */
    public Victim getVictim(Integer id) {

        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "FROM Victim V WHERE V.id = :id";

            Query query = session.createQuery(hql);

            query.setParameter("id", id);

            return (Victim) query.getSingleResult();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    /**
     *
     * @param stalker Stalker object
     * @return list of Victim objects that are Stalked by stalker
     */
    public List getListOfVictims(Stalker stalker) {

        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "FROM Victim V WHERE V.stalker = :stalker";

            Query query = session.createQuery(hql);

            query.setParameter("stalker", stalker);

            return query.list();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     * @param name name of victim
     * @return last coordinates of victim
     */
    public Cordinates getCordinates(String name) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "FROM Victim V WHERE V.name = :name";

            Query query = session.createQuery(hql);

            query.setParameter("name", name);

            Victim v = (Victim) query.getSingleResult();

            Cordinates cordinates = new Cordinates(v.getCordinatesx().toString(), v.getCordinatesy().toString());
            return cordinates;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     *
     * @param victim Victim object
     * @return List of Photo objects maped by victim
     */
    public LinkedList<Photo> getPhotos(Victim victim) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "FROM Photo P WHERE P.victim = :victim";

            Query query = session.createQuery(hql);

            query.setParameter("victim", victim);
            LinkedList<Photo> photos = new LinkedList<>();
            query.list().forEach(element->{
                photos.add((Photo) element);
            });

            return photos;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     *
     * @param photo Photo object that has to be saved in DB
     */
    public void updloadPhoto(Photo photo) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            session.save(photo);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    /**
     *
     * @param name name of Victim object
     * @return Victim object with name = name
     */
    public Victim getVictimByName(String name) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "FROM Victim V WHERE V.name = :name";

            Query query = session.createQuery(hql);

            query.setParameter("name", name);

            return (Victim) query.list().get(0);
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     * @param victim that coordinates we want to update
     */
    public void updateCordinates(Victim victim) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();

            String hql = "UPDATE Victim V set V.cordinatesx=:cordiantesx,V.cordinatesy=:cordinatesy" +
                    " WHERE V.id = :id";

            Query query = session.createQuery(hql);

            query.setParameter("cordiantesx", victim.getCordinatesx());
            query.setParameter("cordinatesy", victim.getCordinatesy());
            query.setParameter("id", victim.getId());

            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }


}
