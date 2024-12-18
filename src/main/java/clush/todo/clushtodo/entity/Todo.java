package clush.todo.clushtodo.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity@Builder@Data
@RequiredArgsConstructor@AllArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long tid;
    Short priority;
    @Nullable
    LocalDateTime complete;
    Boolean delay = false;
    String name;
    String category;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    User user;

}
