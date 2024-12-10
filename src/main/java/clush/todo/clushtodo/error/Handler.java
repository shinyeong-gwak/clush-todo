package clush.todo.clushtodo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<String> handleCustomException(CustomException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error: \"" + exception.myStatus.getMessage() + "\"");
    }
}
