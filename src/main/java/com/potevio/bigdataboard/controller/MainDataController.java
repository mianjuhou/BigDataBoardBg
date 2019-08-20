package com.potevio.bigdataboard.controller;

import com.potevio.bigdataboard.entity.Result;
import com.potevio.bigdataboard.entity.StatusCode;
import com.potevio.bigdataboard.service.MainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/main")
@CrossOrigin
public class MainDataController {

    @Autowired
    private MainDataService service;

    /**
     * 学校情况
     */
    @GetMapping("/schoolinfo")
    public Result getSchoolInfo() {
        HashMap<String, List> schoolInfo = service.getSchoolInfo();
        return new Result(true, StatusCode.OK, "获取学校情况数据成功", schoolInfo);
    }

    /**
     * 学生信息-男女比例
     * 学生人数统计
     */
    @GetMapping("/studentinfo")
    public Result getStudentInfo() {
        Map<String, Map<String, Integer>> schoolInfo = service.getStudentInfo();
        return new Result(true, StatusCode.OK, "获取学校情况数据成功", schoolInfo);
    }

    /**
     * 学校 班级 老师 学生 总人数
     */
    @GetMapping("/fourinfo")
    public Result getFourTotalNum() {
        Map<String, Long> map = service.getFourTotalNum();
        return new Result(true, StatusCode.OK, "获取学校 班级 老师 学生 总人数成功", map);
    }

    /**
     * 办学层次
     * 获取小学初中高中各级学校总数
     */
    @GetMapping("/schoolnum")
    public Result getSchoolTypeNum() {
        Map<String, Integer> map = service.getSchoolTypeNum();
        return new Result(true, StatusCode.OK, "获取小学初中高中各级学校总数据成功", map);
    }

    /**
     * 地图数据
     * 获取各镇小学初中高中学校数量
     */
    @GetMapping("/townschool")
    public Result getTownSchoolInfo() {
        Map<String, Integer[]> map = service.getTownSchoolInfo();
        return new Result(true, StatusCode.OK, "获取获取各镇小学初中高中学校数量成功", map);
    }

    /**
     * 教师信息-学历
     */
    @GetMapping("/teacheredu")
    public Result getTeacherEdu() {
        Map<String, Integer> map = service.getTeacherEdu();
        return new Result(true, StatusCode.OK, "获取各学历教师数量成功", map);
    }


    /**
     * 教师职称
     */
    @GetMapping("/teachertitle")
    public Result getTeacherTitle() {
        Map<String, Integer[]> map = service.getTeacherTitle();
        return new Result(true, StatusCode.OK, "获取各职称老师数量成功", map);
    }

}

