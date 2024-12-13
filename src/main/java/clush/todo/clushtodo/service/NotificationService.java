package clush.todo.clushtodo.service;

import clush.todo.clushtodo.controller.NotiController;
import clush.todo.clushtodo.dto.Noti;
import clush.todo.clushtodo.entity.Notification;
import clush.todo.clushtodo.repository.NotiRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service@Slf4j@RequiredArgsConstructor
public class NotificationService {

    //서비스를 위해 확장한다면 다른 저장공간을 필요로 함.-> 애플리케이션 종료/재시작 시 사라짐.
    private final Map<String,SseEmitter> emitters = new HashMap<>();
    @Autowired
    private NotiRepo notiRepo;

    // 매 분마다 보내야할 알림을 체크하며 전송
    @Scheduled(fixedRate = 60000)
    public void checkAndSendNotifications() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> notifications = notiRepo.findByRingBeforeAndSentFalse(now);
        for (Notification notification : notifications) {
            sendNotification(notification);
        }
    }

    //연결된 유저는 보내고, 안되면 연결된 유저 리스트에서 삭제함
    public void sendNotification(Notification noti) {

        try {
            emitters.get(noti.getUser().getUserId()).send(SseEmitter.event().name("알림").data(noti.getContent()));
            notiRepo.updateSent(noti.getNid());
        } catch (IOException e) {
            emitters.remove(emitters.get(noti.getUser().getUserId()));
        }
    }

    // 할일이 사라지기 10분전 정리 시간을 경고,
    @Scheduled(cron = "50 3 * * * *" )
    public void notifyUncompletedTodo() {
        sendNotification();
    }

    public SseEmitter connect(String userId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userId,emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }
}