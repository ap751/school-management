package com.example.school.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class TaskScheduler {
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendExamReminder() {
        System.out.println("Reminder: Your exams are approaching! Current time: " + LocalDateTime.now());
        //we can write logic to send a mail to all student evey 8 am
    }
}
