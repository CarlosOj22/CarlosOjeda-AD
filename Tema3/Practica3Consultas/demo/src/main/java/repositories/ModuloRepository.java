package repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Modulo;

public class ModuloRepository implements Repository<Modulo> {

    private final Session session;

    public ModuloRepository(Session session){
        this.session = session;
    }

    @Override
    public void save(Modulo modulo) {
        Transaction trx = session.beginTransaction();
        session.save(modulo);
        System.out.println("Modulo guardado con id: " + modulo.getModuloId());
         trx.commit();
    }

    @Override
    public void update(Modulo modulo) {
        Transaction trx = session.beginTransaction();
        session.update(modulo);
        System.out.println("Modulo actualizado con id: " + modulo.getModuloId());
        trx.commit();
    }

    @Override
    public void delete(Modulo modulo) {
        Transaction trx = session.beginTransaction();
        session.delete(modulo);
        System.out.println("Modulo borrado con id: " + modulo.getModuloId());
        trx.commit();
    }

    @Override
    public List<Modulo> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje HQL (Hibernate QL)
        List<Modulo> modulos = session.createQuery("FROM modulos",Modulo.class).getResultList();
        trx.commit();
        return modulos;
    }

    @Override
    public Modulo findOneById(long id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Modulo modulo = session.createQuery("FROM modulos where modulo_id=:id",Modulo.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return modulo;
    }
}