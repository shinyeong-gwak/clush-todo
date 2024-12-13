package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.Noti;
import clush.todo.clushtodo.entity.Notification;
import clush.todo.clushtodo.repository.NotiRepo;
import jakarta.transaction.Transactional;
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
    //Stateful
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
        Noti readyToSend = new Noti(noti.getTag().name(),noti.getDeco(),noti.getMsg());
        //데이터베이스에서 나온 알림인 경우 처리 후 데이터베이스 반영
        sendNotification(noti.getUser().getUserId(), readyToSend);
        notiRepo.updateSent(noti.getNid());
    }

    //데이터베이스에 예약된 전송 / 시간마다 보내는 전송 각각 객체가 다르기에 오버로딩을 통해 사용 가능하게 함.
    @Transactional
    public void sendNotification(String userId, Noti noti) {

        try {
            emitters.get(userId).send(SseEmitter.event().name("알림").data(noti.getContent()));
        } catch (NullPointerException ne){
        } catch (IOException e) {
            emitters.remove(emitters.get(userId));
        }
    }

    // 할일이 사라지기 10분전 정리 시간을 경고
    public void notifyUncompletedTodo(List<String> userList) {
        Noti noti = new Noti("완료된 할 일은 사라지고, 아직 완료하지 않은 할일은 미완료로 넘어갑니다.\n 하루를 마치기 전 할 일을 정리해보세요.","10분 뒤");
        for (String uid : userList) {
            sendNotification(uid,noti);
        }
    }

    public SseEmitter connect(String userId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userId,emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }
}