package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.Month;
import clush.todo.clushtodo.dto.Notification;
import clush.todo.clushtodo.dto.Schedule;
import clush.todo.clushtodo.dto.ScheduleReq;
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
import java.util.Optional;
import java.util.UUID;

import static clush.todo.clushtodo.error.CustomResponse.BAD_REQUEST;
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
        LocalDateTime monthStart = date.withDayOfMonth(1).atStartOfDay();
        LocalDateTime monthEnd = date.withDayOfMonth(date.lengthOfMonth()).atTime(23,59);
        return calRepo.findAllByMonth(monthStart,monthEnd, userId);
    }

    public List<Schedule> getOneDay(LocalDate date, String userId) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        return calRepo.findAllByDay(start,end,userId);
    }

    public UUID editSchedule(UUID cid, User user, Schedule newSchedule) throws CustomException {

        Optional<Calendar> existingCalendar = calRepo.findById(cid); // CID로 기존 데이터 조회

        if (existingCalendar.isPresent()) {
            // 새로운 값이 null이 아니면 set, 아니면 기존 값 유지
            Calendar calendar = existingCalendar.get();
            calendar.setUser(user);
            if (newSchedule.getName() != null) {
                calendar.setName(newSchedule.getName());
            }
            if (newSchedule.getStart() != null) {
                calendar.setStart(newSchedule.getStart());
            }
            if (newSchedule.getEnd() != null) {
                calendar.setEnd(newSchedule.getEnd());
            }
            if (newSchedule.getNeedNoti() != null) {
                calendar.setNeedNoti(newSchedule.getNeedNoti());
            }
            if (newSchedule.getDepth() != null) {
                calendar.setDepth(newSchedule.getDepth());
            }
            if (newSchedule.getTag() != null) {
                calendar.setTag(Calendar.Color.valueOf(newSchedule.getTag()));
            }

            // 업데이트된 객체 저장
            calRepo.saveAndFlush(existingCalendar.get());
            return existingCalendar.get().getCid();
        } else {
            // 만약 기존 데이터가 없다면 적절한 처리 (예: 예외 처리)
            throw new CustomException(BAD_REQUEST);
        }
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
