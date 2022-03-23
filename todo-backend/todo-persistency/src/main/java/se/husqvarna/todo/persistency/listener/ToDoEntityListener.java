package se.husqvarna.todo.persistency.listener;

import se.husqvarna.todo.persistency.entity.ToDoEntity;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class ToDoEntityListener {

    @PrePersist
    public void beforePersist(ToDoEntity toDoEntity) {
        toDoEntity.setInsertDateTime(LocalDateTime.now());
    }

}
