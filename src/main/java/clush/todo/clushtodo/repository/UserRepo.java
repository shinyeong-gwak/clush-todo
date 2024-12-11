package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByUserIdAndPassword(String id, String pw);
}
