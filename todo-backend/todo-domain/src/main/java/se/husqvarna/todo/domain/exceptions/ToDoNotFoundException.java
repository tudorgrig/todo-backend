package se.husqvarna.todo.domain.exceptions;

public class ToDoNotFoundException extends Exception{

    public ToDoNotFoundException(Long toDoId){
        super("ToDo not found with id " + toDoId);
    }
}
