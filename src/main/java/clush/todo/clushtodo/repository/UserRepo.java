package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<String, User> {
}
