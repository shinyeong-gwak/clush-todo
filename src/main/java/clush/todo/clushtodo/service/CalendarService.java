package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.Month;
import clush.todo.clushtodo.dto.Notification;
import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.entity.Calendar;
import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.error.CustomException;
import clush.todo.clushtodo.repository.CalendarRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static clush.todo.clushtodo.error.CustomResponse.DATA_NOT_SAVED;

@Service @Slf4j @RequiredArgsConstructor
public class CalendarService {

    @Autowired
    CalendarRepo calRepo;

    @Autowired
    NotificationService notiSvc;

    public UUID addSchedule(User user, Schedule schedule) throws CustomException {
        Calendar saved = calRepo.saveAndFlush(Calendar.builder()
                        .user(user)
                        .start(schedule.getStart())
                        .end(schedule.getEnd())
                        .depth(schedule.getDepth())
                        .tag(Calendar.Color.valueOf(schedule.getTag()))
                        .name(schedule.getName())
                        .needNoti(schedule.getNeedNoti())
                .build());
        try {
            return saved.getCid();
        } catch (NullPointerException e) {
            throw new CustomException(DATA_NOT_SAVED);
        }
    }

    //상세 보기 x : 캘린더에 뜰 정보만 리턴하기
    public List<Month> getSchedules(LocalDate date, String userId) {
        LocalDate monthStart = date.withDayOfMonth(1);
        LocalDate monthEnd = date.withDayOfMonth(date.lengthOfMonth());
        return calRepo.findAllByMonth(monthStart,monthEnd, userId);
    }

    public List<Schedule> getOneDay(LocalDate date, String userId) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return calRepo.findAllByDay(start,end,userId);
    }

    public UUID editSchedule(UUID cid, Schedule newSchedule) {
        Calendar update = Calendar.builder()
                .cid(cid)
                .name(newSchedule.getName())
                .start(newSchedule.getStart())
                .end(newSchedule.getEnd())
                .needNoti(newSchedule.getNeedNoti())
                .depth(newSchedule.getDepth())
                .build();


        calRepo.saveAndFlush(update);
        return update.getCid();
    }

    public void deleteSchedule(UUID cid) {
        calRepo.deleteById(cid);
    }

    // 매 분마다 알림 울릴 상태가 있는지 확인. (연결 유지 자원 소모)
    @Scheduled(fixedRate = 60000)
    public void delegateAlarm() {
        LocalDateTime startTime = LocalDateTime.now().plusMinutes(10); //10분 뒤에 시작.
        LocalDateTime s = startTime.minusSeconds(startTime.getSecond());
        LocalDateTime e = startTime.plusSeconds(60-startTime.getSecond());

        List<Notification> notifications = calRepo.findByRingBeforeAndSentFalse(s,e);
        for (Notification notification : notifications) {
            notiSvc.notify10MinutesLeft(notification);
        }
    }
}
