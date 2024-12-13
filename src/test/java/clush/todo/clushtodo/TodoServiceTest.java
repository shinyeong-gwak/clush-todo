package clush.todo.clushtodo;

import clush.todo.clushtodo.dto.TaskReq;
import clush.todo.clushtodo.dto.ViewRes;
import clush.todo.clushtodo.entity.Todo;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.repository.TodoRepo;
import clush.todo.clushtodo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepo todoRepo;

    @InjectMocks
    private TodoService todoService;

    User user = new User("test", "password");

    @Test
    public void testAddTodo() {
        TaskReq.Task todo = new TaskReq.Task((short) 1,"Task", "category");

        UUID result = todoService.addTodo(user, todo);
        assertNotNull(result);
        verify(todoRepo, times(1)).saveAndFlush(any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        UUID tid = UUID.randomUUID();
        doNothing().when(todoRepo).deleteById(tid);

        todoService.deleteTodo(tid);

        verify(todoRepo, times(1)).deleteById(tid);
    }

    @Test
    public void testCompleteTodo() {
        UUID tid = UUID.randomUUID();
        todoService.completeTodo(tid);

        verify(todoRepo, times(1)).updateDelay(tid, false);
        verify(todoRepo, times(1)).updatecomplete(tid, any());
    }

    @Test
    public void testUndoCompleteTodo() {
        UUID tid = UUID.randomUUID();
        todoService.undocomplete(tid);

        verify(todoRepo, times(1)).updatecomplete(tid, null);
    }

    @Test
    public void testDelayTodo() {
        UUID tid = UUID.randomUUID();
        todoService.delayTodo(tid);

        verify(todoRepo, times(1)).updatecomplete(tid, null);
        verify(todoRepo, times(1)).updateDelay(tid, true);
    }

    @Test
    public void testGetTodos() {
        String userId = "test";
        List<ViewRes> todos = new ArrayList<>();
        when(todoRepo.findAllByIdAndcompleteFalseAndDelayFalse(userId)).thenReturn(todos);

        List<ViewRes> result = todoService.getTodos(userId);
        assertNotNull(result);
        verify(todoRepo, times(1)).findAllByIdAndcompleteFalseAndDelayFalse(userId);
    }

    @Test
    public void testCleaning() {
        doNothing().when(todoRepo).deleteAllBycomplete(any());
        doNothing().when(todoRepo).updateAllDelay();

        todoService.cleaning();

        verify(todoRepo, times(1)).deleteAllBycomplete(any());
        verify(todoRepo, times(1)).updateAllDelay();
    }
}
