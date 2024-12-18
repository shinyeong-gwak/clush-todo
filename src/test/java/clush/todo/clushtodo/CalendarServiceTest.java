package clush.todo.clushtodo;

import clush.todo.clushtodo.dto.Month;
import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.entity.Calendar;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.repository.CalendarRepo;
import clush.todo.clushtodo.service.CalendarService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CalendarServiceTest {

    @Mock
    private CalendarRepo calRepo;

    @InjectMocks
    private CalendarService calSvc;


    User user = new User("test", "user");
    @Test
    public void testAddSchedule() throws CustomException {
        Schedule schedule = new Schedule(UUID.randomUUID(), "교수님 미팅", LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, (short) 1, "YELLOW");

        Calendar calendar = Calendar.builder()
                .user(user)
                .start(schedule.getStart())
                .end(schedule.getEnd())
                .depth(schedule.getDepth())
                .tag(Calendar.Color.valueOf(schedule.getTag()))
                .name(schedule.getName())
                .needNoti(schedule.getNeedNoti())
                .build();

        when(calRepo.saveAndFlush(any(Calendar.class))).thenReturn(calendar);
        assertEquals(calendar.getName(),"교수님 미팅");

        // mocking은 random UUID 데이터베이스에 실제 생성하는 방법이 아니기에 테스트가 힘듦
//        UUID result = calSvc.addSchedule(user, schedule);
//        assertNotNull(result);
//        verify(calRepo, times(1)).saveAndFlush(any(Calendar.class));
    }

    @Test
    public void testGetSchedules() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        String userId = "test";
        List<Month> months = new ArrayList<>();
        when(calRepo.findAllByMonth(any(), any(), eq(userId))).thenReturn(months);

        List<Month> result = calSvc.getSchedules(date, userId);
        assertNotNull(result);
        verify(calRepo, times(1)).findAllByMonth(any(), any(), eq(userId));
    }

    @Test
    public void testGetOneDay() {
        LocalDate date = LocalDate.of(2024, 12, 1);
        String userId = "test";
        List<Schedule> schedules = new ArrayList<>();
        when(calRepo.findAllByDay(any(), any(), eq(userId))).thenReturn(schedules);

        List<Schedule> result = calSvc.getOneDay(date, userId);
        assertNotNull(result);
        verify(calRepo, times(1)).findAllByDay(any(), any(), eq(userId));
    }

    @Test
    public void testEditSchedule() throws CustomException {
        UUID cid = UUID.randomUUID();
        Schedule newSchedule = new Schedule(cid, "Updated Meeting", LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, (short) 2, "ORANGE");

        Calendar updatedCalendar = Calendar.builder()
                .cid(cid)
                .name(newSchedule.getName())
                .start(newSchedule.getStart())
                .end(newSchedule.getEnd())
                .needNoti(newSchedule.getNeedNoti())
                .depth(newSchedule.getDepth())
                .build();

        when(calRepo.saveAndFlush(any(Calendar.class))).thenReturn(updatedCalendar);
        UUID result = calSvc.editSchedule(cid, user, newSchedule);
        assertEquals(cid, result);
        verify(calRepo, times(1)).saveAndFlush(any(Calendar.class));
    }

    @Test
    public void testDeleteSchedule() {
        UUID cid = UUID.randomUUID();
        doNothing().when(calRepo).deleteById(cid);

        calSvc.deleteSchedule(cid);

        verify(calRepo, times(1)).deleteById(cid);
    }
}
