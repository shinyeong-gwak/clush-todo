package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUserIdAndPassword(String id, String pw);
}
