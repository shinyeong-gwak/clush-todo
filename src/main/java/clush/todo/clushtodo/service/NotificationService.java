package clush.todo.clushtodo.service;

import clush.todo.clushtodo.controller.NotiController;
import clush.todo.clushtodo.entity.Notification;
import clush.todo.clushtodo.repository.NotiRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service@Slf4j@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private NotiRepo notiRepo;

    @Autowired
    private NotiController notiController;

    @Scheduled(fixedRate = 60000)
    public void checkAndSendNotifications() {
        LocalDateTime now = LocalDateTime.now();

        List<Notification> notifications = notiRepo.findByRingBeforeAndSentFalse(now);
        for (Notification notification : notifications) {
            notiController.sendNotification(notification);
            notiRepo.updateSent(notification.getNid());
        }
    }
}