package se.husqvarna.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.domain.exceptions.ToDoNotFoundException;
import se.husqvarna.todo.persistency.repository.ToDoRepository;

import java.util.List;

import static org.apache.logging.log4j.util.Strings.isNotEmpty;

/**
 * Service class consisting of all major actions for a ToDo NOSONAR
 */
@Service
@Transactional(readOnly = true)
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    /**
     * Save new ToDo NOSONAR
     * @param newToDo new object to be saved
     * @return saved object
     */
    @Transactional
    public ToDo saveNewToDo(ToDo newToDo){
        return toDoRepository.save(newToDo);
    }

    /**
     * Find all TODOs NOSONAR
     * @return all TODOs stored
     */
    public List<ToDo> findAll(){
        return toDoRepository.findAll();
    }

    /**
     * Delete a ToDo by using it's primary key NOSONAR
     * @param id primary key value
     */
    @Transactional
    public void deleteToDoById(Long id) {
        toDoRepository.deleteById(id);
    }

    /**
     * Find ToDo by primary key NOSONAR
     * @param todoId primary key value
     * @return found ToDo NOSONAR
     * @throws ToDoNotFoundException when target could not be found
     */
    public ToDo findById(Long todoId) throws ToDoNotFoundException{
        return toDoRepository.findById(todoId).orElseThrow(() -> new ToDoNotFoundException(todoId));
    }

    /**
     * Update ToDo based on entered input fields NOSONAR
     * @param id primary key value
     * @param title new title value, or null/empty if title must not be updated
     * @param completed new completed value, or null if completed status must not be updated
     * @param order new order value, or null if new order value must not be updated
     * @return updated object
     * @throws ToDoNotFoundException if target object could not be found
     */
    @Transactional
    public ToDo updateToDo(Long id, String title, Boolean completed, Integer order) throws ToDoNotFoundException{
        ToDo found = findById(id);
        if(completed != null){
            found.setCompleted(completed);
        }
        if(isNotEmpty(title)){
            found.setTitle(title);
        }
        if(order != null) {
            found.setOrder(order);
        }
        return toDoRepository.update(found);
    }

    /**
     * Delete all objects
     */
    @Transactional
    public void deleteAll() {
        toDoRepository.deleteAll();
    }
}
