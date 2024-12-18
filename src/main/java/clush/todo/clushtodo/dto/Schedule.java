package clush.todo.clushtodo.dto;

import clush.todo.clushtodo.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Data@AllArgsConstructor@NoArgsConstructor
public class Schedule {
    @Nullable
    private Long cid;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean needNoti;
    // 필요성 의문 -> 프론트에서 생성/소비로 바뀔 가능성
    private Short depth;
    private String tag;

    public Schedule(Long cid, String name, LocalDateTime start, LocalDateTime end, Boolean needNoti, Short depth, Calendar.Color tag) {
        this.cid = cid;
        this.name = name;
        this.start = start;
        this.end = end;
        this.needNoti = needNoti;
        this.depth = depth;
        this.tag = tag.name();
    }
}
