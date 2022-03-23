package se.husqvarna.todo.api.mapper;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import se.husqvarna.todo.api.dto.ToDoDto;
import se.husqvarna.todo.domain.ToDo;

public final class ToDoDtoMapper {

    private ToDoDtoMapper(){}

    public static ToDoDto mapToDto(ToDo toDo) {
        ToDoDto toDoDto = new ToDoDto();
        toDoDto.setId(toDo.getId());
        toDoDto.setTitle(toDo.getTitle());
        toDoDto.setCompleted(toDo.isCompleted());
        toDoDto.setUrl(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+"/"+toDo.getId());
        toDoDto.setOrder(toDo.getOrder());
        return toDoDto;
    }


    public static ToDo mapToDomain(ToDoDto newToDo) {
        ToDo toDo = new ToDo();
        toDo.setId(newToDo.getId());
        toDo.setTitle(newToDo.getTitle());
        toDo.setOrder(newToDo.getOrder());
        toDo.setCompleted(newToDo.isCompleted());
        return toDo;
    }

}
