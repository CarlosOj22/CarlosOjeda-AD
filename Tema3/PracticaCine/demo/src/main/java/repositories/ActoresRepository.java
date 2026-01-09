package repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Actor;

public class ActoresRepository implements Repository<Actor> {
    
    //OBJETO REGLAMENTARIO SESSION
    private final Session session;

    public ActoresRepository(Session session){
        this.session=session;
    }

    @Override
    public void save(Actor actor){
        Transaction trx = session.beginTransaction();
        session.save(actor);
        trx.commit();
        System.out.println("Actor guardada con id: " +  actor.getId());
    }

    @Override
    public void update(Actor actor) {
        Transaction trx = session.beginTransaction();
        session.update(actor);
        trx.commit();
        System.out.println("Actor actualizado con id: " + actor.getId());
    }

    @Override
    public void delete(Actor actor) {
        Transaction trx = session.beginTransaction();
        session.delete(actor);
        trx.commit();
        System.out.println("Actor borrado con id: " + actor.getId());
    }

    @Override
    public List<Actor> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        List<Actor> actores = session.createQuery("FROM actores",Actor.class).getResultList();
        trx.commit();
        return actores;
    }

    @Override
    public Actor findOneById(int id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Actor actor = session.createQuery("FROM actores where actorId=:id",Actor.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return actor;
    }
}
