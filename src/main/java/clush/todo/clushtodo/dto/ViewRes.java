package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@AllArgsConstructor
public class ViewRes {
    UUID tid;

    Short priority;
    LocalDateTime complate;
    Boolean delay;

    String name;
    String category;


}

