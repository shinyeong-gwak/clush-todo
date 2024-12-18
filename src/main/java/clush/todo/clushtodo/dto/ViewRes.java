package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@AllArgsConstructor
public class ViewRes {
    Long tid;

    Short priority;
    LocalDateTime complete;
    Boolean delay;

    String name;
    String category;


}

