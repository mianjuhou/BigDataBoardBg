package com.potevio.bigdataboard.task;

import com.potevio.bigdataboard.service.TeacherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

//@Component
@EnableScheduling
public class FixRateScheduleTask {

//    @Autowired
    private TeacherDataService service;

    @Scheduled(fixedRate = 120000)
    private void configureTasks() {
        for (int i = 1; i <= 11; i++) {
            service.syncFunc(i);
        }
        System.out.println(new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
    }

}
