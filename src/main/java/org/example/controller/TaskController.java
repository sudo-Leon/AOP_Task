package org.example.controller;

import org.example.logging.annotation.LogHttp;
import org.example.exception.NotFoundException;
import org.example.model.Task;
import org.example.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @LogHttp  // Теперь логирование включается только для этого метода
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    @LogHttp  // Логируем только получение задачи по ID
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
    }
}