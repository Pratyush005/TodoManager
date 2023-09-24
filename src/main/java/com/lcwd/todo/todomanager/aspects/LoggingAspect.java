package com.lcwd.todo.todomanager.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.lcwd.todo.todomanager.controller.TodoController.*(..))")
    public void logBeforeRunningController() {
        logger.info("................... Executing the controller classes ...................");
        logger.info("++++++++++++++++++++++ Fetching your desired results ++++++++++++++++++++++");
    }

    @Before("execution(* com.lcwd.todo.todomanager.controller.TodoController.createTodoHandler(..))")
    public void logBeforeCreateController() {
        logger.info("++++++++++++++++ Creating the todo ++++++++++++++++++");
    }

    @After("execution(* com.lcwd.todo.todomanager.controller.TodoController.createTodoHandler(..))")
    public void logAfterCreateController() {
        logger.info("------------------- Todo created successfully-----------------");
        logger.info("+++++++++++++++++ Thank you ++++++++++++++++++++++");
    }

    @After("execution(* com.lcwd.todo.todomanager.controller.TodoController.*(..))")
    public void logAfterRunningController() {
        logger.info(".................Controller executed successfully...................");
    }
}
