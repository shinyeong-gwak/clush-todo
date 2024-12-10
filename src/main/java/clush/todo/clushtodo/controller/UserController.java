package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.LoginReq;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<Object> simpleLogin(@RequestBody LoginReq loginReq) {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
