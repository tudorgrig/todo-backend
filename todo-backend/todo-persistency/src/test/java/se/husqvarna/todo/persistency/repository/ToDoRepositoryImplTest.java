package se.husqvarna.todo.persistency.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.persistency.entity.ToDoEntity;
import se.husqvarna.todo.persistency.repository.jpa.ToDoJpaRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.husqvarna.todo.persistency.mapper.ToDoEntityMapper.mapToEntity;

@ExtendWith(MockitoExtension.class)
class ToDoRepositoryImplTest {

    private static final Long ID = 2L;
    private static final String TITLE = "TITLE";
    private static final int ORDER = 2;

    @Mock
    private ToDoJpaRepository toDoJpaRepository;

    @InjectMocks
    private ToDoRepositoryImpl toDoRepository;

    private static ToDo domain;
    private static ToDoEntity entity;

    @BeforeAll
    static void setup(){
        domain = createDomain();
        entity = mapToEntity(domain);
    }

    @Test
    void shouldSaveNewToDo(){
        when(toDoJpaRepository.save(any(ToDoEntity.class))).thenReturn(entity);
        ToDo result = toDoRepository.save(domain);

        assertThat(result.getId()).isEqualTo(ID);
    }

    @Test
    void shouldFindAllToDo(){
        when(toDoJpaRepository.findAll()).thenReturn(singletonList(entity));
        List<ToDo> response = toDoRepository.findAll();

        assertThat(response).hasSize(1);
        assertThat(response.get(0).getId()).isEqualTo(ID);
    }

    @Test
    void shouldFindToDoById(){
        when(toDoJpaRepository.findById(ID)).thenReturn(Optional.of(entity));
        Optional<ToDo> response = toDoRepository.findById(ID);
        assertThat(response).isPresent();
    }

    @Test
    void shouldNotFindToDoById(){
        when(toDoJpaRepository.findById(ID)).thenReturn(Optional.empty());
        Optional<ToDo> response = toDoRepository.findById(ID);
        assertThat(response).isEmpty();
    }

    @Test
    void shouldDeleteAll(){
        toDoRepository.deleteAll();
        verify(toDoJpaRepository).deleteAll();
    }

    @Test
    void shouldDeleteOne(){
        toDoRepository.deleteById(ID);
        verify(toDoJpaRepository).deleteById(ID);
    }

    private static ToDo createDomain() {
        var toDo = new ToDo();
        toDo.setId(ID);
        toDo.setTitle(TITLE);
        toDo.setOrder(ORDER);
        return toDo;
    }
}