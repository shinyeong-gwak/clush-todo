package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@RequiredArgsConstructor
public class ViewRes {
    UUID tid;

    Short priority;
    LocalDateTime complate;
    Boolean delay;

    String name;
    String category;

    public ViewRes(UUID tid, Short priority, LocalDateTime complate, Boolean delay, String name, String category) {
        this.tid = tid;
        this.priority = priority;
        this.complate = complate;
        this.delay = delay;
        this.name = name;
        this.category = category;
    }
}

