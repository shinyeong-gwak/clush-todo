package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class ScheduleReq {
    Schedule schedule;
    String userId;
}
