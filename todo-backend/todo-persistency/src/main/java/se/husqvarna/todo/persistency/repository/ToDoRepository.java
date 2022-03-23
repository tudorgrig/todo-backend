package se.husqvarna.todo.persistency.repository;

import se.husqvarna.todo.domain.ToDo;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface
 */
public interface ToDoRepository {

    /**
     * Save new domain object to store
     * @param toDo domain object to be stored NOSONAR
     * @return saved object
     */
    ToDo save(ToDo toDo);

    /**
     * Find all ToDos NOSONAR
     * @return list of existing ToDos in store
     */
    List<ToDo> findAll();

    /**
     * Find ToDo by it's primary key NOSONAR
     * @param todoId primary key value
     * @return Optional holding value, empty Optional otherwise
     */
    Optional<ToDo> findById(Long todoId);

    /**
     * Update target ToDo NOSONAR
     * @param toUpdate ToDo to be updated NOSONAR
     * @return updated object
     */
    ToDo update(ToDo toUpdate);

    /**
     * Delete ToDo by primary key value NOSONAR
     * @param id primary key
     */
    void deleteById(Long id);

    /**
     * Delete all ToDos from store NOSONAR
     */
    void deleteAll();
}
