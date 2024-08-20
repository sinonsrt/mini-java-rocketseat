package br.com.lucasmarchiori.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
        UserModel user = this.userRepository.findByUsername(userModel.getUsername());

        if(user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
        }

        String passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());


        userModel.setPassword(passwordHashred);

        UserModel createdUser = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
