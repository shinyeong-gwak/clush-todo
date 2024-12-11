package clush.todo.clushtodo.controller;

import clush.todo.clushtodo.entity.Notification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/sse")
public class NotiController {

    private final Map<String,SseEmitter> emitters = new HashMap<>();
    @GetMapping("/connect")
    public SseEmitter connect(@RequestParam("userId") String userId) {
        SseEmitter emitter = new SseEmitter();
        emitters.put(userId,emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    public void sendNotification(Notification noti) {

        try {
            emitters.get(noti.getUser().getUserId()).send(SseEmitter.event().name("알림").data(noti.getContent()));
        } catch (IOException e) {
            emitters.remove(emitters.get(noti.getUser().getUserId()));
        }
    }

}
