package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.TaskReq;
import clush.todo.clushtodo.dto.ViewRes;
import clush.todo.clushtodo.entity.Todo;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.error.CustomResponse;
import clush.todo.clushtodo.repository.TodoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service@Slf4j@RequiredArgsConstructor
public class TodoService {
    @Autowired
    TodoRepo todoRepo;

    @Autowired
    NotificationService notiSvc;

    public Long addTodo(String userId, TaskReq.Task todo) {
        Todo saved = todoRepo.saveAndFlush(Todo.builder()
                .userId(userId)
                .name(todo.getName())
                .category(todo.getCategory())
                .priority(todo.getPriority())
                .delay(false)
                .build());
        
        return saved.getTid();
    }

    public void deleteTodo(Long tid) {
        todoRepo.deleteById(tid);
    }

    public void completeTodo(Long tid) {
        todoRepo.updateDelay(tid,false);
        todoRepo.updatecomplete(tid, LocalDateTime.now());
    }

    public void undocomplete(Long tid) {
        todoRepo.updatecomplete(tid, null);
    }

    public void delayTodo(Long tid,Boolean delay) {
        todoRepo.updatecomplete(tid,null);
        todoRepo.updateDelay(tid,delay);
    }

    public void editTodo(Long tid, TaskReq.Task newTodo) throws CustomException {
        Todo todo = todoRepo.findById(tid).orElseThrow( () -> new CustomException(CustomResponse.NOT_FOUND) );

        Optional.ofNullable(newTodo.getName()).ifPresent(todo::setName);
        Optional.ofNullable(newTodo.getCategory()).ifPresent(todo::setCategory);
        Optional.ofNullable(newTodo.getPriority()).ifPresent(todo::setPriority);

        todoRepo.saveAndFlush(todo);
    }
    public List<ViewRes> getTodos(String userId) {
        return todoRepo.findAllByIdAndDelayFalse(userId);
    }

    public List<ViewRes> getTodoscomplete(String userId) {
        return todoRepo.findAllByIdAndcompleteTrue(userId);
    }

    public List<ViewRes> getTodosDelay(String userId) {
        return todoRepo.findAllByIdAndDelayTrue(userId);
    }


    @Scheduled(cron = "0 0 4 * * *")
    public void cleaning() {
        // 이전날 완료한 항목 지우기
        todoRepo.deleteAllByCompleteNotNullAndCompleteBefore(LocalDateTime.now());
        // 아직 완료되지 않는 항목 미완료 풀로 이동시키기
        todoRepo.updateAllDelay();
        System.out.println("cleaned");
    }

    // 할일을 완료하지 않는 사람들의 리스트를 가져다 줌.
    @Scheduled(cron= "0 50 3 * * *")
    public void delegateNotify() {
        List<String> userList=  todoRepo.findUserIdAllByCompleteTrue();
        userList.forEach(System.out::println);
        notiSvc.notifyUncompletedTodo(userList);
        System.out.println("notify");
    }

    public void patchPriority() {
    }
}
