package se.husqvarna.todo.persistency.repository;

import org.springframework.stereotype.Component;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.persistency.entity.ToDoEntity;
import se.husqvarna.todo.persistency.mapper.ToDoEntityMapper;
import se.husqvarna.todo.persistency.repository.jpa.ToDoJpaRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static se.husqvarna.todo.persistency.mapper.ToDoEntityMapper.mapToDomain;
import static se.husqvarna.todo.persistency.mapper.ToDoEntityMapper.mapToEntity;

@Component
public class ToDoRepositoryImpl implements ToDoRepository{

    private final ToDoJpaRepository toDoJpaRepository;

    public ToDoRepositoryImpl(ToDoJpaRepository toDoJpaRepository){
        this.toDoJpaRepository = toDoJpaRepository;
    }

    @Override
    public ToDo save(@Valid ToDo toDo) {
        ToDoEntity toDoEntity = mapToEntity(toDo);
        return mapToDomain(toDoJpaRepository.save(toDoEntity));
    }

    @Override
    public List<ToDo> findAll() {
        return toDoJpaRepository.findAll().stream()
                .map(ToDoEntityMapper::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ToDo update(@Valid ToDo found) {
        ToDoEntity toDoEntity = toDoJpaRepository.save(mapToEntity(found));
        return mapToDomain(toDoEntity);
    }

    @Override
    public Optional<ToDo> findById(Long todoId) {
        return toDoJpaRepository.findById(todoId).map(ToDoEntityMapper::mapToDomain);
    }

    @Override
    public void deleteById(Long id) {
        toDoJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        toDoJpaRepository.deleteAll();
    }

}
