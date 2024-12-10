package clush.todo.clushtodo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id @Column(name = "user_id")
    String userId;
    String password;
}
