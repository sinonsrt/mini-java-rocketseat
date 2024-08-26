package br.com.lucasmarchiori.todolist.task;

import br.com.lucasmarchiori.todolist.task.interfaces.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel) {
        TaskModel createdTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
}
