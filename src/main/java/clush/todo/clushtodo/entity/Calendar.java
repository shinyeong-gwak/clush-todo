package clush.todo.clushtodo.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Entity@Builder
@AllArgsConstructor@RequiredArgsConstructor
@Data
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cid;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    User user;
    String name = "이벤트";
    LocalDateTime start;
    LocalDateTime end;
    @Column(name = "need_noti")
    Boolean needNoti;
    Short depth;
    Color tag = Color.RED;

    //알림을 위해 추가
    Boolean sent = false;

    public enum Color {
        RED,ORANGE,YELLOW,GREEN,BLUE,PURPLE,PINK;

    }

}
