package clush.todo.clushtodo.dto;

import lombok.Data;

import java.time.LocalDateTime;

public class ToDoDTO {
    @Data
    public static class BasicDTO {
        Short priority;
        String name;
        String category;
    }

    @Data
    public static class addReq {
        String userId;
        BasicDTO todo;
    }

    @Data
    public static class ViewRes {
        Short priority;
        LocalDateTime complate;
        Boolean delay;
        String name;
        String category;
    }

}
