package se.husqvarna.todo.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se.husqvarna.todo.api.dto.JsonPatch;
import se.husqvarna.todo.api.dto.ToDoDto;
import se.husqvarna.todo.api.mapper.ToDoDtoMapper;
import se.husqvarna.todo.domain.ToDo;
import se.husqvarna.todo.domain.exceptions.ToDoNotFoundException;
import se.husqvarna.todo.service.ToDoService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static se.husqvarna.todo.api.mapper.ToDoDtoMapper.mapToDomain;
import static se.husqvarna.todo.api.mapper.ToDoDtoMapper.mapToDto;

@RestController
@RequestMapping
@Tag(name = "ToDo Controller", description = "ToDo API requests")
public class ToDoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ToDoController.class);

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService){
        this.toDoService = toDoService;
    }

    @PostMapping
    @Operation(summary = "Create a new ToDo")
    public ResponseEntity<ToDoDto> createNewToDo(@RequestBody @Valid ToDoDto newToDo){
        var createdToDo = toDoService.saveNewToDo(mapToDomain(newToDo));
        return new ResponseEntity<>(mapToDto(createdToDo), CREATED);
    }

    @GetMapping
    @Operation(summary= "Get all ToDos")
    public List<ToDoDto> getAllToDos(){
        return toDoService.findAll().stream()
                .map(ToDoDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @Operation(summary = "Get details about one ToDo")
    public ToDoDto getById(@PathVariable("id") Long todoId){
        try {
            return mapToDto(toDoService.findById(todoId));
        } catch (ToDoNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new ResponseStatusException(BAD_REQUEST);
        }
    }


    @DeleteMapping
    @Operation(summary = "Delete all")
    public ResponseEntity<Void> deleteAll(){
        toDoService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete last added ToDo")
    public ResponseEntity<Void> deleteOneToDo(@PathVariable("id") Long id){
        toDoService.deleteToDoById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "{id}")
    @Operation(summary = "Patch a toDo")
    public ResponseEntity<ToDoDto> patchToDo(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch){
        try{
            ToDo patchedToDo = toDoService.updateToDo(id, jsonPatch.getTitle(), jsonPatch.getCompleted(), jsonPatch.getOrder());
            return ResponseEntity.ok(mapToDto(patchedToDo));
        }  catch (ToDoNotFoundException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new ResponseStatusException(BAD_REQUEST);
        }
    }

}
