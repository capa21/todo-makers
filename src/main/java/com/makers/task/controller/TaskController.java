package com.makers.task.controller;

import com.makers.task.exception.ResourceNotFoundException;
import com.makers.task.model.Task;
import com.makers.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    //Get All Tasks
    @GetMapping("/tasks")
     public List<Task> getAllTask(){
         return taskRepository.findAll();
     }

     //Create a new Task
    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task task) {
        return taskRepository.save(task);
    }

    //Get a single Task
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable(value = "id") Long id_task) {
        return this.findTaskWithException(id_task);
    }

    //Update a task
    @PutMapping("/tasks/{id}")
    public Task updateTask(@PathVariable(value = "id") Long id_task, @Valid @RequestBody Task taskDetails){
        Task task = this.findTaskWithException(id_task);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.getCompleted());

        return taskRepository.save(task);
    }

    //Delete a task
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable(value = "id") Long id_task) {
        Task task = findTaskWithException(id_task);
        taskRepository.delete(task);

        return ResponseEntity.ok().build();
    }

    private Task findTaskWithException(Long id_task) {
        return taskRepository.findById(id_task)
                .orElseThrow(() -> new ResourceNotFoundException("Task","id",id_task));
    }

}
