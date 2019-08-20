package com.potevio.bigdataboard.controller;

import com.potevio.bigdataboard.entity.Result;
import com.potevio.bigdataboard.entity.StatusCode;
import com.potevio.bigdataboard.service.TeacherDataService;
import com.potevio.bigdataboard.ws.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//@RestController
@RequestMapping("/school")
@CrossOrigin
public class TeacherDataController {

//    @Autowired
    private TeacherDataService service;


    @PostMapping("/ws")
    public Result sendWebsocket(String msg) {
        try {
            MyWebSocket.sendInfo(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(true, StatusCode.OK, "获Websocket发送消息成功");
    }

    @GetMapping("/sync/bsk/{index}")
    public Result<Map<String, List>> findBSKNumSync(@PathVariable int index) {
        service.syncFunc(index);
        return new Result<>(true, StatusCode.OK, "同步成功");
    }

    @GetMapping("/bsk")
    public Result<Map<String, List>> findBSKNum() {
        Map<String, List> res = service.findBSKNum();
        return new Result<>(true, StatusCode.OK, "获取备授课次数成功", res);
    }

    @GetMapping("/bk")
    public Result<Map<String, List>> findBKTop10() {
        Map<String, List> res = service.findBKTop10();
        return new Result<>(true, StatusCode.OK, "获取备课top10成功", res);
    }

    @GetMapping("/sk")
    public Result<Map<String, List>> findSKTop10() {
        Map<String, List> res = service.findSKTop10();
        return new Result<>(true, StatusCode.OK, "获取授课top10成功", res);
    }

    @GetMapping("/resource")
    public Result<Map<String, Long>> findResourceNums() {
        Map<String, Long> res = service.findResourceNums();
        return new Result<>(true, StatusCode.OK, "获取教育资源上传总数,微课上传个数,习题上传个数成功", res);
    }

    @GetMapping("/resourcetop")
    public Result<Map<String, List>> findResourceTop10() {
        Map<String, List> res = service.findResourceTop10();
        return new Result<>(true, StatusCode.OK, "获取教学资源积累top10成功", res);
    }

    @GetMapping("/resourcemonthtop")
    public Result<Map<String, List>> findResourceMonthTop10() {
        Map<String, List> res = service.findResourceMonthTop10();
        return new Result<>(true, StatusCode.OK, "获取月度教学资源积累top10成功", res);
    }

    @GetMapping("/resourcemonth")
    public Result<Map<String, List>> findResourceMonth() {
        Map<String, List> res = service.findResourceMonth();
        return new Result<>(true, StatusCode.OK, "获取学校上传教学资源统计成功", res);
    }

    @GetMapping("/classmoth")
    public Result<Map<String, List>> findClassMonth() {
        Map<String, List> res = service.findClassMonth();
        return new Result<>(true, StatusCode.OK, "获取学校活跃度统计成功", res);
    }

    @GetMapping("/bclass")
    public Result<Map<String, List>> findBClass() {
        Map<String, List> res = service.findBClass();
        return new Result<>(true, StatusCode.OK, "获取课前发布top10成功", res);
    }

    @GetMapping("/mclass")
    public Result<Map<String, List>> findMClass() {
        Map<String, List> res = service.findMClass();
        return new Result<>(true, StatusCode.OK, "获取课中发布top10成功", res);
    }

    @GetMapping("/aclass")
    public Result<Map<String, List>> findAClass() {
        Map<String, List> res = service.findAClass();
        return new Result<>(true, StatusCode.OK, "获取课后发布top10成功", res);
    }

}
