package Java.com.paymentapp.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<K, E> {
    Optional<E> findById(K id);
    E save(E entity);
    E update(E entity);
    boolean deleteById(K id);

    List<E> findAll();
}
