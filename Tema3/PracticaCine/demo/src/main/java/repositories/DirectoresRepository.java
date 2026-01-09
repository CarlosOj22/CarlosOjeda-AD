package repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Director;

public class DirectoresRepository implements Repository<Director> {
    
    //OBJETO REGLAMENTARIO SESSION
    private final Session session;

    public DirectoresRepository(Session session){
        this.session=session;
    }

    @Override
    public void save(Director director){
        Transaction trx = session.beginTransaction();
        session.save(director);
        trx.commit();
        System.out.println("Director guardada con id: " +  director.getId());
    }

    @Override
    public void update(Director director) {
        Transaction trx = session.beginTransaction();
        session.update(director);
        trx.commit();
        System.out.println("Director actualizado con id: " + director.getId());
    }

    @Override
    public void delete(Director director) {
        Transaction trx = session.beginTransaction();
        session.delete(director);
        trx.commit();
        System.out.println("Director borrado con id: " + director.getId());
    }

    @Override
    public List<Director> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        List<Director> directores = session.createQuery("FROM directores",Director.class).getResultList();
        trx.commit();
        return directores;
    }

    @Override
    public Director findOneById(int id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Director director = session.createQuery("FROM directores where directorId=:id",Director.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return director;
    }
}
