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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @Mock
    private TodoRepo todoRepo;

    @InjectMocks
    private TodoService todoSvc;

    User user = new User("test", "user");

    @Test
    public void testAddTodo() {
        TaskReq.Task todo = new TaskReq.Task((short) 1,"Task", "category");

        Long result = 10L;
        assertNotNull(result);
        verify(todoRepo, times(1)).saveAndFlush(any(Todo.class));
    }

    @Test
    public void testDeleteTodo() {
        Long tid = 10L;
        doNothing().when(todoRepo).deleteById(tid);

        todoSvc.deleteTodo(tid);

        verify(todoRepo, times(1)).deleteById(tid);
    }

    @Test
    public void testCompleteTodo() {
        Long tId = 10L;
        todoSvc.completeTodo(tId);

        verify(todoRepo, times(1)).updateDelay(tId, false);
        verify(todoRepo, times(1)).updatecomplete(tId, any());
    }

    @Test
    public void testUndoCompleteTodo() {
        Long tId = 10L;
        todoSvc.undocomplete(tId);

        verify(todoRepo, times(1)).updatecomplete(tId, null);
    }

    @Test
    public void testDelayTodo() {
        Long tId = 10L;
        todoSvc.delayTodo(tId,true);

        verify(todoRepo, times(1)).updatecomplete(tId, null);
        verify(todoRepo, times(1)).updateDelay(tId, true);
    }

    @Test
    public void testGetTodos() {
        String userId = "test";
        List<ViewRes> todos = new ArrayList<>();
        when(todoRepo.findAllByIdAndDelayFalse(userId)).thenReturn(todos);

        List<ViewRes> result = todoSvc.getTodos(userId);
        assertNotNull(result);
        verify(todoRepo, times(1)).findAllByIdAndDelayFalse(userId);
    }

    @Test
    public void testCleaning() {
        doNothing().when(todoRepo).deleteAllBycompleteBefore(any());
        doNothing().when(todoRepo).updateAllDelay();

        todoSvc.cleaning();

        verify(todoRepo, times(1)).deleteAllBycompleteBefore(any());
        verify(todoRepo, times(1)).updateAllDelay();
    }
}
