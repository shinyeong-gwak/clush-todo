package clush.todo.clushtodo.dto;

import clush.todo.clushtodo.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@AllArgsConstructor
public class Month {
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String tag;

    public Month(String name, LocalDateTime start, LocalDateTime end, Calendar.Color tag) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.tag = tag.name();
    }
}
