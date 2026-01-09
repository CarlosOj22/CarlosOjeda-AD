package repositories;

import entities.Pelicula;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PeliculaRepository implements Repository<Pelicula> {
    
    //OBJETO REGLAMENTARIO SESSION
    private final Session session;

    public PeliculaRepository(Session session){
        this.session=session;
    }

    @Override
    public void save(Pelicula pelicula){
        Transaction trx = session.beginTransaction();
        session.save(pelicula);
        trx.commit();
        System.out.println("Pelicula guardada con id: " +  pelicula.getId());
    }

    @Override
    public void update(Pelicula pelicula) {
        Transaction trx = session.beginTransaction();
        session.update(pelicula);
        trx.commit();
        System.out.println("Pelicula actualizado con id: " + pelicula.getId());
    }

    @Override
    public void delete(Pelicula pelicula) {
        Transaction trx = session.beginTransaction();
        session.delete(pelicula);
        trx.commit();
        System.out.println("Pelicula borrado con id: " + pelicula.getId());
    }

    @Override
    public List<Pelicula> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        List<Pelicula> peliculas = session.createQuery("FROM peliculas",Pelicula.class).getResultList();
        trx.commit();
        return peliculas;
    }

    @Override
    public Pelicula findOneById(int id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Pelicula pelicula = session.createQuery("FROM peliculas where peliculaId=:id",Pelicula.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return pelicula;
    }
}
