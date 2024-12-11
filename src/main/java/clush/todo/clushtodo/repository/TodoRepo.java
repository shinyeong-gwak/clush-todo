package clush.todo.clushtodo.repository;

import clush.todo.clushtodo.dto.ViewRes;
import clush.todo.clushtodo.entity.Todo;
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
public interface TodoRepo extends JpaRepository<Todo,UUID> {
    @Transactional@Modifying
    @Query("UPDATE Todo t SET t.complate = :now WHERE t.tid = :tid")
    void updateComplate(@Param("tid") UUID tid, @Param("now") LocalDateTime now);

    @Transactional@Modifying
    @Query("UPDATE Todo t SET t.delay = :b WHERE t.tid = :tid")
    void updateDelay(@Param("tid") UUID tid, @Param("b") boolean b);

    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complate,t.delay,t.name,t.category) FROM Todo t WHERE t.user.userId = :userId AND t.complate = null AND t.delay = false")
    List<ViewRes> findAllByIdAndComplateFalseAndDelayFalse(@Param("userId") String userId);

    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complate,t.delay,t.name,t.category) FROM Todo t WHERE t.user.userId = :userId AND t.complate != null")
    List<ViewRes> findAllByIdAndComplateTrue(@Param("userId") String userId);

    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complate,t.delay,t.name,t.category) FROM Todo t WHERE t.user.userId = :userId AND t.delay = true")
    List<ViewRes> findAllByIdAndDelayTrue(@Param("userId") String userId);
}
