package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalendarRepo extends JpaRepository<UUID, Calendar> {
}
