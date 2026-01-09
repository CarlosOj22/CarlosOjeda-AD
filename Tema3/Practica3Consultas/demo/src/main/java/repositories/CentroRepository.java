package repositories;

import java.util.List;
import entities.Centro;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class CentroRepository implements Repository<Centro> {
    
    private final Session session;

    public CentroRepository(Session session){
        this.session = session;
    }

    @Override
    public void save(Centro centro) {
        Transaction trx = session.beginTransaction();
        session.save(centro);
        System.out.println("Centro guardado con id: " + centro.getCentroId());
         trx.commit();
    }

    @Override
    public void update(Centro centro) {
        Transaction trx = session.beginTransaction();
        session.update(centro);
        System.out.println("Centro actualizado con id: " + centro.getCentroId());
        trx.commit();
    }

    @Override
    public void delete(Centro centro) {
        Transaction trx = session.beginTransaction();
        session.delete(centro);
        System.out.println("Centro borrado con id: " + centro.getCentroId());
        trx.commit();
    }

    @Override
    public List<Centro> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje HQL (Hibernate QL)
        List<Centro> centros = session.createQuery("FROM centros",Centro.class).getResultList();
        trx.commit();
        return centros;
    }

    @Override
    public Centro findOneById(long id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Centro centro = session.createQuery("FROM centros where centro_id=:id",Centro.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return centro;
    }
}