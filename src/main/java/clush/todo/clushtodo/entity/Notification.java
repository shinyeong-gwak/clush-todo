package clush.todo.clushtodo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity@Data
public class Notification {
    @Id
    @Column(columnDefinition = "BINARY(16) DEFAULT (UUID_TO_BIN(UUID(), 1))")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UUID nid;

    String msg;
    Calendar.Color tag;

    String deco;

    LocalDateTime ring;

    Boolean sent = false;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    User user;

    public String getContent() {
        return String.format("(%s) %s\n%s\n",tag.name(),msg,deco);
    }
}
