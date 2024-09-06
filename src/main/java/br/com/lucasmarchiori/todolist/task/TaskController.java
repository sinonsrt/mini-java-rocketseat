package br.com.lucasmarchiori.todolist.task;

import br.com.lucasmarchiori.todolist.task.interfaces.ITaskRepository;
import br.com.lucasmarchiori.todolist.task.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @GetMapping("/")
    public ResponseEntity list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        List<TaskModel> tasks = this.taskRepository.findAllByUserId((UUID) userId);

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var userId = request.getAttribute("userId");

        taskModel.setUserId((UUID) userId);

        LocalDateTime currentDate = LocalDateTime.now();

        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start/end date must be greater than the current date");
        }

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The start date must be less than the end date");
        }

        TaskModel createdTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request) {
        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found!");
        }

        var userId = request.getAttribute("userId");

        if(!task.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User don't have permission to update this task!");
        }

        Utils.copyNonNullProperties(taskModel, task);

        var updatedTask = this.taskRepository.save(task);

        return ResponseEntity.ok().body(updatedTask);
    }
}
