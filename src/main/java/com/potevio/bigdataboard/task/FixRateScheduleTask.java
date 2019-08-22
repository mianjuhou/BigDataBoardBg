package com.potevio.bigdataboard.task;

import com.potevio.bigdataboard.service.TeacherDataService;
import com.potevio.bigdataboard.ws.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class FixRateScheduleTask {

    @Autowired
    private TeacherDataService service;

    @Scheduled(fixedRate = 300000)
    private void configureTasks() {
        for (int i = 1; i <= 11; i++) {
            service.syncFunc(i);
        }
        System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
    }

    @Scheduled(fixedRate = 3600000)
    private void taskOneHour() {
        try {
            MyWebSocket.sendInfo("20");
            System.out.println("WS发送" + "20");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
