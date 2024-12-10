package clush.todo.clushtodo.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Schedule {
    private String name;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private Boolean needNoti;
    private Short depth;
    private String tag;
}
