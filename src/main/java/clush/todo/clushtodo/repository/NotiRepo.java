package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.entity.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotiRepo extends JpaRepository<Notification, UUID> {
    List<Notification> findByRingBeforeAndSentFalse(LocalDateTime now);

    @Modifying@Transactional
    @Query("UPDATE Notification n SET n.sent = true WHERE n.nid = :nid")
    void updateSent(@Param("nid")UUID nid);
}
