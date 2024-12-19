package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.LoginReq;
import clush.todo.clushtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static clush.todo.clushtodo.error.CustomResponse.ALREADY_EXISTS;
import static clush.todo.clushtodo.error.CustomResponse.INVALID_CREDENTIALS;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> simpleLogin(@RequestBody LoginReq loginReq) {

        if (userService.simpleLogin(loginReq.id(), loginReq.pw()))
            return ResponseEntity.ok(HttpStatus.OK);
        return ResponseEntity.status(401).body(INVALID_CREDENTIALS);
    }

    @PostMapping("/register")
    public ResponseEntity<?> simpleRegister(@RequestBody LoginReq loginReq) {
        if (userService.register(loginReq.id(),loginReq.pw()))
            return ResponseEntity.ok(HttpStatus.OK);
        return ResponseEntity.status(404).body(ALREADY_EXISTS);
    }
}
