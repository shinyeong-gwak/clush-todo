package clush.todo.clushtodo;

import clush.todo.clushtodo.repository.UserRepo;
import clush.todo.clushtodo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClushTodoApplicationTests {

    @Mock
    UserService userSvcMock;

    @Mock
    UserRepo userRepoMock;

    @Test
    void testLoginValid() {
        when(userSvcMock.simpleLogin("test","user")).thenReturn(true);
        boolean result = userSvcMock.simpleLogin("test","user");
        assertTrue(result);
    }

    @Test
    void testLoginInvalid() {
        when(userSvcMock.simpleLogin("test1","test")).thenReturn(false);
        boolean result = userSvcMock.simpleLogin("test1","test");
        assertFalse(result);
    }


}
