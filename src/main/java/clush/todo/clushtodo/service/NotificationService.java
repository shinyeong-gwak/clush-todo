package clush.todo.clushtodo.service;

import clush.todo.clushtodo.dto.NotiMsg;
import clush.todo.clushtodo.dto.Notification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service@Slf4j@RequiredArgsConstructor
public class NotificationService {

    //서비스를 위해 확장한다면 다른 저장공간을 필요로 함.-> 애플리케이션 종료/재시작 시 사라짐.
    //Stateful
    private final Map<String,SseEmitter> emitters = new HashMap<>();

    //10분 남았는지 알리기 위해 알림 메시지를 생성
    public void notify10MinutesLeft(Notification noti) {
        NotiMsg readyToSend = new NotiMsg(noti.getTag().name(),noti.getName(),"일정이 10분 뒤 시작됩니다.\n잊지 마세요!");
        sendNotification(noti.getUserId(), readyToSend);
    }

    //알림 전송 코드
    @Transactional
    public void sendNotification(String userId, NotiMsg noti) {

        try {
            emitters.get(userId).send(SseEmitter.event().name("알림").data(noti.getContent()));
        } catch (NullPointerException ne){
        } catch (IOException e) {
            emitters.remove(emitters.get(userId));
        }
    }

    // 할일이 사라지기 10분전 정리 시간을 경고
    public void notifyUncompletedTodo(List<String> userList) {
        NotiMsg notiMsg = new NotiMsg("완료된 할 일은 사라지고, 아직 완료하지 않은 할일은 미완료로 넘어갑니다.\n 하루를 마치기 전 할 일을 정리해보세요.","10분 뒤");
        for (String uid : userList) {
            sendNotification(uid, notiMsg);
        }
    }
    //유저가 브라우저에서 페이지를 열 때 연결.
    public SseEmitter connect(String userId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userId,emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }
}