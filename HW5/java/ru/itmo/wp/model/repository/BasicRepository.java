package ru.itmo.wp.model.repository;


import java.util.List;

public interface BasicRepository<T> {
    T findBy(Object... args);

    T find(long id);

    List<T> findAll();

    void save(T toSave, Object... args);

    long findCount();
}
