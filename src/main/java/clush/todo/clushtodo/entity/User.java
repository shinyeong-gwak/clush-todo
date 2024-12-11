package clush.todo.clushtodo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity@Data
public class User {
    @Id @Column(name = "user_id")
    String userId;
    String password;
}
