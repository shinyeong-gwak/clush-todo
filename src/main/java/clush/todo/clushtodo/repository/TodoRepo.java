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

@Repository
public interface TodoRepo extends JpaRepository<Todo,Long> {
    @Transactional@Modifying
    @Query("UPDATE Todo t SET t.complete = :now WHERE t.tid = :tid")
    void updatecomplete(@Param("tid") Long tid, @Param("now") LocalDateTime now);

    @Transactional@Modifying
    @Query("UPDATE Todo t SET t.delay = :b WHERE t.tid = :tid")
    void updateDelay(@Param("tid") Long tid, @Param("b") boolean b);

    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complete,t.delay,t.name,t.category) FROM Todo t WHERE t.userId = :userId AND t.delay = false")
    List<ViewRes> findAllByIdAndDelayFalse(@Param("userId") String userId);


    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complete,t.delay,t.name,t.category) FROM Todo t WHERE t.userId = :userId AND t.complete IS NOT null")
    List<ViewRes> findAllByIdAndcompleteTrue(@Param("userId") String userId);

    @Query("SELECT new clush.todo.clushtodo.dto.ViewRes(t.tid,t.priority,t.complete,t.delay,t.name,t.category) FROM Todo t WHERE t.user.userId = :userId AND t.delay = true")
    List<ViewRes> findAllByIdAndDelayTrue(@Param("userId") String userId);

    @Transactional@Modifying
    void deleteAllByCompleteNotNullAndCompleteBefore(LocalDateTime now);

    @Transactional@Modifying
    @Query("UPDATE Todo t SET t.delay = true WHERE t.delay = false")
    void updateAllDelay();

    @Query("SELECT DISTINCT t.userId FROM Todo t WHERE t.complete IS null")
    List<String> findUserIdAllByCompleteTrue();

    void deleteAllBycompleteBefore(Object any);
}
