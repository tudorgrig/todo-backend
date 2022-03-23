package se.husqvarna.todo.persistency.mapper;

import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.persistency.entity.ToDoEntity;

public final class ToDoEntityMapper {

    private ToDoEntityMapper(){}

    public static ToDo mapToDomain(ToDoEntity save) {
        ToDo toDo = new ToDo();
        toDo.setId(save.getId());
        toDo.setTitle(save.getTitle());
        toDo.setCompleted(save.isCompleted());
        toDo.setOrder(save.getOrder());
        return toDo;
    }

    public static ToDoEntity mapToEntity(ToDo toDo) {
        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setId(toDo.getId());
        toDoEntity.setTitle(toDo.getTitle());
        toDoEntity.setCompleted(toDo.isCompleted());
        toDoEntity.setOrder(toDo.getOrder());
        return toDoEntity;
    }
}
