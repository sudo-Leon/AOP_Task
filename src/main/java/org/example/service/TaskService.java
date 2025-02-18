package org.example.service;

import org.example.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {
    private final Map<Long, Task> tasks = new HashMap<>(); // Хранилище задач
    private final AtomicLong idGenerator = new AtomicLong(1); // Генератор уникальных ID

    public Task createTask(Task task) {
        long id = idGenerator.getAndIncrement();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    public Optional<Task> getTaskById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> getAllTasks() {return new ArrayList<>(tasks.values());
    }

    //Обновление задачи
    public Optional<Task> updateTask(Long id, Task updatedTask) {
        if (tasks.containsKey(id)) {
            updatedTask.setId(id);
            tasks.put(id, updatedTask);
            return Optional.of(updatedTask);
        }
        return Optional.empty();
    }

    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}