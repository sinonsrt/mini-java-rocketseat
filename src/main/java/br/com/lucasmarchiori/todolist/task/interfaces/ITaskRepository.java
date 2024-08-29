package br.com.lucasmarchiori.todolist.task.interfaces;

import br.com.lucasmarchiori.todolist.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findAllByUserId(UUID userId);
}
