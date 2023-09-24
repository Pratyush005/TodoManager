package com.lcwd.todo.todomanager.service;

import com.lcwd.todo.todomanager.exception.ResourceNotFoundException;
import com.lcwd.todo.todomanager.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    List<Todo> todos = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(TodoService.class);

    //create todo method
    public Todo createTodo(Todo todo) {
        //create
        todos.add(todo);
        logger.info("Todos {}", this.todos);
        return todo;
    }

    //get all the todo list
    public List<Todo> getAllTodos() {
        return todos;
    }

    public Todo getSingleTodo(Integer todoId) {
        Todo todo = todos.stream().filter(t -> todoId.equals(t.getId())).findAny().orElseThrow(() -> new ResourceNotFoundException("ToDo not found with the given ID", HttpStatus.NOT_FOUND));
//                .get();
        logger.info("TODO : {}", todo);
        return todo;
    }

    public Todo updateTodo(Todo todo, Integer todoId) {
        List<Todo> todoupdatedList = todos.stream().map(t -> {
            if (t.getId() == todoId) {
                //perform update operation
                return t;
            } else {
                return t;
            }
        }).collect(Collectors.toList());

        todos = todoupdatedList;
        todo.setId(todoId);
        return todo;
    }

    public void deleteTodo(Integer todoId) {
        logger.info("Deleting the data");
        List<Todo> todoList = todos.stream().filter(t -> !todoId.equals(t.getId())).collect(Collectors.toList());
        todos = todoList;
    }
}