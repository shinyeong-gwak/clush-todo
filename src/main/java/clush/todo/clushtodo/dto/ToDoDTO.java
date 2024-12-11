package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class ToDoDTO {
    @Data
    public static class BasicDTO {

        Short priority;
        String name;
        String category;

    }

    @Data
    public static class AddReq {
        String userId;
        BasicDTO todo;
    }
}
