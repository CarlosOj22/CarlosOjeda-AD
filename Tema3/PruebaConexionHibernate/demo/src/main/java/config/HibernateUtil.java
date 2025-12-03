package config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.out.println("Error de conexión: " + ex.getMessage());
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory get(){
        return sessionFactory;
    }
}

