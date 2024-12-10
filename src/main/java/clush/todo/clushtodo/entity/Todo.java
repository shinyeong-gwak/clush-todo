package clush.todo.clushtodo.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Todo {
    @Id @Column(columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1))")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID tid;
    @ManyToOne @JoinColumn(name = "user_id")
    User userId;
    Short priority;
    @Nullable
    LocalDateTime complate;
    Boolean delay = false;
    String name;
    String category;

}
