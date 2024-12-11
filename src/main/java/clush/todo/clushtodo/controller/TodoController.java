package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.IdRes;
import clush.todo.clushtodo.dto.ToDoDTO;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.service.TodoService;
import clush.todo.clushtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoSvc;

    @Autowired
    UserService userSvc;

    @PostMapping
    public ResponseEntity<?> addTodo(@RequestBody ToDoDTO.AddReq addReq) throws CustomException {
        User user = userSvc.validate(addReq.getUserId());
        return ResponseEntity.ok(new IdRes(todoSvc.addTodo(user,addReq.getTodo())));
    }

    @PatchMapping("/{id}/complate/t")
    public ResponseEntity<?> complateTodo(@PathVariable("id") String tid) {
        UUID tId = UUID.fromString(tid);
        todoSvc.complateTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/complate/f")
    public ResponseEntity<?> complateUndoTodo(@PathVariable("id") String tid) {
        UUID tId = UUID.fromString(tid);
        todoSvc.undoComplate(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/delay")
    public ResponseEntity<?> delayTodo(@PathVariable("id") String tid) {
        UUID tId = UUID.fromString(tid);
        todoSvc.delayTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String tid) {
        UUID tId = UUID.fromString(tid);
        todoSvc.deleteTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTodo(@PathVariable("id") String tid, @RequestBody ToDoDTO.BasicDTO newTodo) throws CustomException {
        UUID tId = UUID.fromString(tid);
        todoSvc.editTodo(tId,newTodo);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTodos(@RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodos(userId));
    }

    @GetMapping("/complate")
    public ResponseEntity<?> getTodosComplate(
            @RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodosComplate(userId));
    }

    @GetMapping("/delay")
    public ResponseEntity<?> getTodosDelay(@RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodosDelay(userId));
    }


}
