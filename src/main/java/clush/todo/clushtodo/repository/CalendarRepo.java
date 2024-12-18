package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.dto.Month;
import clush.todo.clushtodo.dto.Notification;
import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.entity.Calendar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CalendarRepo extends JpaRepository<Calendar, UUID> {
    @Query("SELECT new clush.todo.clushtodo.dto.Month(c.name,c.start,c.end,c.tag) FROM Calendar c WHERE c.user.userId = :userId AND (c.start <= :end OR c.end >= :start ) ")
    List<Month> findAllByMonth(@Param("start") LocalDateTime start,
                               @Param("end") LocalDateTime end,
                               @Param("userId") String userId);
    @Query("SELECT new clush.todo.clushtodo.dto.Schedule(c.cid, c.name,c.start,c.end,c.needNoti,c.depth,c.tag) FROM Calendar c WHERE c.user.userId = :userId AND (c.start <= :end OR c.end >= :start ) ")
    List<Schedule> findAllByDay(@Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end,
                                @Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("SELECT new clush.todo.clushtodo.dto.Notification(c.user.userId,c.name,c.tag)  FROM Calendar c WHERE ( c.start BETWEEN :now1 AND :now2 ) AND c.sent=false AND c.needNoti =true")
    List<Notification> findByRingBeforeAndSentFalse(LocalDateTime now1, LocalDateTime now2);
}
