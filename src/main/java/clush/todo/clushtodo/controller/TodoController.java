package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.ToDoDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @PostMapping
    public ResponseEntity<Object> addTodo(@RequestBody ToDoDTO.BasicDTO addReq) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/complate")
    public ResponseEntity<Object> complateTodo(@PathVariable("id") String tid) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/delay")
    public ResponseEntity<Object> delayTodo(@PathVariable("id") String tid) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable("id") String tid) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editTodo(@PathVariable("id") String tid) {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ToDoDTO.ViewRes>> getTodos(@RequestParam("userId") String userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }


}
