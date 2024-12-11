package clush.todo.clushtodo.dto;

import clush.todo.clushtodo.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@AllArgsConstructor
public class Schedule {
    private UUID cid;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean needNoti;
    private Short depth;
    private String tag;

    public Schedule(UUID cid, String name, LocalDateTime start, LocalDateTime end, Boolean needNoti, Short depth, Calendar.Color tag) {
        this.cid = cid;
        this.name = name;
        this.start = start;
        this.end = end;
        this.needNoti = needNoti;
        this.depth = depth;
        this.tag = tag.name();
    }
}
