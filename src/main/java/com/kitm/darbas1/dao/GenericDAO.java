package com.kitm.darbas1.dao;

import com.kitm.darbas1.Models.Author;
import javafx.collections.ObservableList;

import java.util.List;

public interface GenericDAO<T> {
    /**
     * Find entity by ID
     */

    T findById(int id);

    /**
     * Create a new entity
     *
     * @param fName
     * @param lastName
     * @param email
     * @param city
     */

    void create(String fName, String lastName, String email, String city);

    /**
     * Update entity
     * @param entity
     *
     */

    void update(T entity);

    /**
     * Delete an entity
     *
     * @param id
     */

    void delete(int id);

    /**
     * Find all entities by type
     */

    List<T> all();

    ObservableList<T> findAll();
}
