package repositories;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entities.Alumno;

public class AlumnoRepository implements Repository<Alumno> {

    private final Session session;

    public AlumnoRepository(Session session){
        this.session = session;
    }

    @Override
    public void save(Alumno alumno) {
        Transaction trx = session.beginTransaction();
        session.save(alumno);
        trx.commit();
        System.out.println("Alumno guardado con id: " + alumno.getPersonaId());
    }

    @Override
    public void update(Alumno alumno) {
        Transaction trx = session.beginTransaction();
        session.update(alumno);
        trx.commit();
        System.out.println("Alumno actualizado con id: " + alumno.getPersonaId());
    }

    @Override
    public void delete(Alumno alumno) {
        Transaction trx = session.beginTransaction();
        session.delete(alumno);
        trx.commit();
        System.out.println("Alumno borrado con id: " + alumno.getPersonaId());
    }

    @Override
    public List<Alumno> findAll() {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        List<Alumno> centros = session.createQuery("FROM alumnos",Alumno.class).getResultList();
        trx.commit();
        return centros;
    }

    @Override
    public Alumno findOneById(long id) {
        Transaction trx=session.beginTransaction();
        //Utilizamos notacion lenguaje SQL
        Alumno alumno = session.createQuery("FROM alumnos where alumnoId=:id",Alumno.class)
        .setParameter("id", id).getSingleResult();
        trx.commit();
        return alumno;
    }
}