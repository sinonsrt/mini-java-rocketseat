package br.com.lucasmarchiori.todolist.user;

import br.com.lucasmarchiori.todolist.user.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getUsername());

        UserModel user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            System.out.println(user);
            System.out.println("User already exists");

            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
        }

        UserModel createdUser = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
