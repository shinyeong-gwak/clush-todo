package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.dto.IdRes;
import clush.todo.clushtodo.dto.TaskReq;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.service.TodoService;
import clush.todo.clushtodo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    TodoService todoSvc;

    @Autowired
    UserService userSvc;

    @PostMapping
    public ResponseEntity<?> addTodo(@RequestBody TaskReq addReq) throws CustomException {
        User user = userSvc.validate(addReq.getUserId());
        return ResponseEntity.ok(new IdRes(todoSvc.addTodo(user,addReq.getTodo())));
    }

    @PatchMapping("/{id}/complete/t")
    public ResponseEntity<?> completeTodo(@PathVariable("id") String tid) {
        Long tId = Long.parseLong(tid);
        todoSvc.completeTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/complete/f")
    public ResponseEntity<?> completeUndoTodo(@PathVariable("id") String tid) {
        Long tId = Long.parseLong(tid);
        todoSvc.undocomplete(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}/delay")
    public ResponseEntity<?> delayTodo(@PathVariable("id") String tid) {
        Long tId = Long.parseLong(tid);
        todoSvc.delayTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") String tid) {
        Long tId = Long.parseLong(tid);
        todoSvc.deleteTodo(tId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTodo(@PathVariable("id") String tid, @RequestBody TaskReq.Task newTodo) throws CustomException {
        Long tId = Long.parseLong(tid);
        todoSvc.editTodo(tId,newTodo);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTodos(@RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodos(userId));
    }

    @GetMapping("/complete")
    public ResponseEntity<?> getTodoscomplete(
            @RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodoscomplete(userId));
    }

    @GetMapping("/delay")
    public ResponseEntity<?> getTodosDelay(@RequestParam("userId") String userId) throws CustomException {
        userSvc.validate(userId);
        return ResponseEntity.ok(todoSvc.getTodosDelay(userId));
    }

    @PatchMapping("/{id}/order")
    public ResponseEntity<?> switchOrder() {
        todoSvc.patchPriority();
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
