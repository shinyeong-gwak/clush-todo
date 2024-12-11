package clush.todo.clushtodo.error;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomException extends Exception {
    CustomResponse myStatus;
}
