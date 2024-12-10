package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TodoRepo extends JpaRepository<Todo,UUID> {
}
