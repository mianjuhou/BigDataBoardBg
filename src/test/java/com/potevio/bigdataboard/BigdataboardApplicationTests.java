package com.potevio.bigdataboard;

import com.potevio.bigdataboard.service.MainDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BigdataboardApplicationTests {

    @Autowired
    private MainDataService service;

    @Test
    public void contextLoads() {
//        service.getTeacherTitle();
    }


}
