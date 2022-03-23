package se.husqvarna.todo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.domain.exceptions.ToDoNotFoundException;
import se.husqvarna.todo.persistency.repository.ToDoRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.COMPLETABLE_FUTURE;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * I have not added all tests as this test class will be very similar in development strategy as ToDoRepositoryImplTest
 * and I have added only tests that bring something new
 */
@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    private static final Long ID = 2L;
    private static final String TITLE = "TITLE";
    private static final int ORDER = 2;
    private static final boolean COMPLETED = true;

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoService toDoService;

    @Test
    void shouldThrowExceptionWhenNoToDoFoundById(){
        when(toDoRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ToDoNotFoundException.class,
                () -> toDoService.findById(ID));
    }

    @Test
    void shouldPatchOnlyToDoTitle() throws ToDoNotFoundException {
        when(toDoRepository.findById(ID)).thenReturn(Optional.of(createDomain()));
        toDoService.updateToDo(ID,"new title", null, null);
        ArgumentCaptor<ToDo> argument = ArgumentCaptor.forClass(ToDo.class);
        verify(toDoRepository).update(argument.capture());
        assertThat(argument.getValue().getTitle()).isEqualTo("new title");
        assertThat(argument.getValue().getOrder()).isEqualTo(ORDER);
        assertThat(argument.getValue().isCompleted()).isEqualTo(COMPLETED);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void shouldPatchCompletedAndOrder(String title) throws ToDoNotFoundException {
        when(toDoRepository.findById(ID)).thenReturn(Optional.of(createDomain()));
        toDoService.updateToDo(ID,title, !COMPLETED, ORDER+1);
        ArgumentCaptor<ToDo> argument = ArgumentCaptor.forClass(ToDo.class);
        verify(toDoRepository).update(argument.capture());
        assertThat(argument.getValue().getTitle()).isEqualTo(TITLE);
        assertThat(argument.getValue().getOrder()).isEqualTo(ORDER+1);
        assertThat(argument.getValue().isCompleted()).isEqualTo(!COMPLETED);
    }


    private static ToDo createDomain() {
        var toDo = new ToDo();
        toDo.setId(ID);
        toDo.setTitle(TITLE);
        toDo.setOrder(ORDER);
        toDo.setCompleted(COMPLETED);
        return toDo;
    }
}