package clush.todo.clushtodo.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<String> handleCustomException(CustomException exception) {
        logger.warn("Exception occurred: {}", exception.myStatus.getMessage());
        return ResponseEntity.status(exception.myStatus.getCode()).body("error: \"" + exception.myStatus.getMessage() + "\"");
    }
}
