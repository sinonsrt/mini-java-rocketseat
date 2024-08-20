package br.com.lucasmarchiori.todolist.user.interfaces;

import br.com.lucasmarchiori.todolist.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
}
