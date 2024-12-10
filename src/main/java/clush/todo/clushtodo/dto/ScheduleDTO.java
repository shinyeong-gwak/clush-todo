package clush.todo.clushtodo.dto;

import lombok.Data;

public class ScheduleDTO {
    @Data
    public static class AddReq {
        Schedule schedule;
        String userId;
    }
}
