package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.ToDoDTO;
import clush.todo.clushtodo.dto.ViewRes;
import clush.todo.clushtodo.entity.Todo;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.error.CustomResponse;
import clush.todo.clushtodo.repository.TodoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service@Slf4j@RequiredArgsConstructor
public class TodoService {
    @Autowired
    TodoRepo todoRepo;

    public UUID addTodo(User user, ToDoDTO.BasicDTO todo) {
        Todo saved = todoRepo.saveAndFlush(Todo.builder()
                .user(user)
                .name(todo.getName())
                .category(todo.getCategory())
                .priority(todo.getPriority())
                .build());
        
        return saved.getTid();
    }

    public void deleteTodo(UUID tid) {
        todoRepo.deleteById(tid);
    }

    public void complateTodo(UUID tid) {
        todoRepo.updateDelay(tid,false);
        todoRepo.updateComplate(tid, LocalDateTime.now());
    }

    public void undoComplate(UUID tid) {
        todoRepo.updateComplate(tid, null);
    }

    public void delayTodo(UUID tid) {
        todoRepo.updateComplate(tid,null);
        todoRepo.updateDelay(tid,true);
    }

    public void editTodo(UUID tid, ToDoDTO.BasicDTO newTodo) throws CustomException {
        Todo todo = todoRepo.findById(tid).orElseThrow( () -> new CustomException(CustomResponse.NOT_FOUND) );

        Optional.ofNullable(newTodo.getName()).ifPresent(todo::setName);
        Optional.ofNullable(newTodo.getCategory()).ifPresent(todo::setCategory);
        Optional.ofNullable(newTodo.getPriority()).ifPresent(todo::setPriority);

        todoRepo.saveAndFlush(todo);
    }

    public List<ViewRes> getTodos(String userId) {
        return todoRepo.findAllByIdAndComplateFalseAndDelayFalse(userId);
    }

    public List<ViewRes> getTodosComplate(String userId) {
        return todoRepo.findAllByIdAndComplateTrue(userId);
    }

    public List<ViewRes> getTodosDelay(String userId) {
        return todoRepo.findAllByIdAndDelayTrue(userId);
    }
}
