package clush.todo.clushtodo.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class Calendar {
    @Id @Column(columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1))")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID cid;
    @ManyToOne @JoinColumn(name = "user_id")
    User userId;
    String name = "이벤트";
    LocalDateTime start;
    LocalDateTime end;
    @Column(name = "need_noti")
    Boolean needNoti;
    Short depth;
    Color tag = Color.RED;

    enum Color {
        RED,ORANGE,YELLOW,GREEN,BLUE,PURPLE,PINK
    }

}
