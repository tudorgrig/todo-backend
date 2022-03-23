package se.husqvarna.todo.persistency.mapper;


import org.junit.jupiter.api.Test;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.persistency.entity.ToDoEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static se.husqvarna.todo.persistency.mapper.ToDoEntityMapper.mapToDomain;
import static se.husqvarna.todo.persistency.mapper.ToDoEntityMapper.mapToEntity;

class ToDoEntityMapperTest {


    private static final Long ID = 2L;
    private static final String TITLE = "TITLE";
    private static final int ORDER = 2;

    @Test
    void shouldMapEntityToDomain(){
        ToDoEntity toDoEntity = createEntity();
        ToDo result = mapToDomain(toDoEntity);
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getTitle()).isEqualTo(TITLE);
        assertThat(result.getOrder()).isEqualTo(ORDER);
    }

    @Test
    void shouldMapDomainToEntity(){
        ToDo domain = createDomain();
        ToDoEntity result  = mapToEntity(domain);
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getTitle()).isEqualTo(TITLE);
        assertThat(result.getOrder()).isEqualTo(ORDER);
    }

    private ToDo createDomain() {
        var toDo = new ToDo();
        toDo.setId(ID);
        toDo.setTitle(TITLE);
        toDo.setOrder(ORDER);
        return toDo;
    }

    private ToDoEntity createEntity() {
        var todoEntity = new ToDoEntity();
        todoEntity.setId(ID);
        todoEntity.setTitle(TITLE);
        todoEntity.setOrder(ORDER);
        return todoEntity;
    }
}