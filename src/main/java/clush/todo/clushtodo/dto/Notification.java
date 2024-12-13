package clush.todo.clushtodo.dto;

import clush.todo.clushtodo.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class Notification {
    String userId;
    String name;
    Calendar.Color tag;
}
