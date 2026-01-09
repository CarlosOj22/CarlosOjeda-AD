package repositories;

import java.util.List;

public interface Repository<T> { //La T sirve para cualquier objeto que implemente la interfaz
    //Operaciones CRUD
    void save(T t);
    List<T> findAll(); //DELVUELVE TODOS
    T findOneById(long id); //DEVUELVE UNO
    void update(T t);
    void delete(T t);

    //METODOS FINDALL y FINDONEBYID
}
