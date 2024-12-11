package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TaskReq {

    String userId;
    Task todo;
    @Data@AllArgsConstructor
    public static class Task {

        Short priority;
        String name;
        String category;

    }

}
