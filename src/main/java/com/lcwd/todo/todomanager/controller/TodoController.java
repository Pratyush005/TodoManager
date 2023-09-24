package com.lcwd.todo.todomanager.controller;

import com.lcwd.todo.todomanager.exception.ExceptionResponse;
import com.lcwd.todo.todomanager.exception.ResourceNotFoundException;
import com.lcwd.todo.todomanager.models.Todo;
import com.lcwd.todo.todomanager.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class TodoController {

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    Random random = new Random();

    @PostMapping("/todos")
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo) {

//        String str = null;
//        logger.info("{}",str.length());
//        Integer.parseInt("11kjzxdgndfn");

        int id = random.nextInt(9999999);

        todo.setId(id);
        //create date with system default current date
        Date currentDate = new Date();
        logger.info("current date {}", currentDate);
        logger.info("todo date {}", todo.getTodoDate());
        todo.setAddedDate(currentDate);
        //create todos
        logger.info("Creating logger!!!");

        //call service to create todo
        Todo todo1 = todoService.createTodo(todo);

        return new ResponseEntity<>(todo1, HttpStatus.CREATED);
    }

    //get all todo methods
    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodoHandler() {
        List<Todo> allTodos = todoService.getAllTodos();
        logger.info("List of todos"+ allTodos);
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    //get single todo from the list
    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getSingleTodoHandler(@PathVariable Integer todoId) {
        logger.info("Getting todo");
        Todo singleTodo = todoService.getSingleTodo(todoId);
        return new ResponseEntity<>(singleTodo, HttpStatus.OK);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodoHandler(@RequestBody Todo todoWithNewDetails, @PathVariable Integer todoId) {
        Todo todo1 = todoService.updateTodo(todoWithNewDetails, todoId);
        return new ResponseEntity<>(todo1, HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodoHandler(@PathVariable Integer todoId) {
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>("Your deleted request is accepted and deleted successfully", HttpStatus.ACCEPTED);
    }

    //Defining exception handler
    @ExceptionHandler(value = {NullPointerException.class, NumberFormatException.class})
    public ResponseEntity<String> nullPointerException(Exception ex) {
        System.out.println(ex.getMessage());
        System.out.println("Null pointer exception generated");
        return new ResponseEntity<>("Null pointer exception generated" + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Handling ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundHandler(ResourceNotFoundException ex) {
        logger.error("ERROR {}", ex.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setSuccess(false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
