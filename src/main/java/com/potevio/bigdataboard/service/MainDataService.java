package com.potevio.bigdataboard.service;

import com.potevio.bigdataboard.dao.MainDataDao;
import com.potevio.bigdataboard.pojo.StudentTeacherNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class MainDataService {

    @Autowired
    private MainDataDao dao;


    private List<StudentTeacherNum> getSchoolStudnetTecherNumSub(String schoolType) {
        List<Object[]> list1 = dao.findSchoolStudentNum(schoolType);
        List<StudentTeacherNum> ret = new ArrayList<>();
        List<Integer> ids1 = new ArrayList<>();
        list1.forEach(item -> {
            StudentTeacherNum bean = new StudentTeacherNum();
            for (int i = 0; i < item.length; i++) {
                Object obj = item[i];
                if (i == 0) {
                    bean.setName((String) obj);
                } else if (i == 1) {
                    Integer id = (Integer) obj;
                    bean.setId(id);
                    ids1.add(id);
                } else if (i == 2) {
                    bean.setSnum(((BigInteger) obj).intValue());
                }
            }
            ret.add(bean);
        });
        List<Object[]> list10 = dao.findSchoolTeacherNum(ids1);
        list10.forEach(item -> {
            Byte id = (Byte) item[0];
            Integer tnum = ((BigInteger) item[1]).intValue();
            for (int i = 0; i < ret.size(); i++) {
                if (ret.get(i).getId().equals(new Integer(id))) {
                    ret.get(i).setTnum(tnum);
                    break;
                }
            }
        });
        return ret;
    }

    public HashMap<String, List> getSchoolInfo() {
        HashMap<String, List> retMap = new HashMap<>();
        retMap.put("school0", getSchoolStudnetTecherNumSub("1"));
        retMap.put("school1", getSchoolStudnetTecherNumSub("2"));
        retMap.put("school2", getSchoolStudnetTecherNumSub("3"));
        return retMap;
    }


    public Map<String, Map<String, Integer>> getStudentInfo() {
        //1 小学 2 初中 3 高中
        List<Object[]> list = dao.findStudentGenderNum();
        Map<String, Map<String, Integer>> maps = new HashMap<String, Map<String, Integer>>();
        maps.put("小学", new HashMap<String, Integer>());
        maps.put("初中", new HashMap<String, Integer>());
        maps.put("高中", new HashMap<String, Integer>());
        maps.get("小学").put("男", 0);
        maps.get("小学").put("女", 0);
        maps.get("初中").put("男", 0);
        maps.get("初中").put("女", 0);
        maps.get("高中").put("男", 0);
        maps.get("高中").put("女", 0);
        for (int i = 0; i < list.size(); i++) {
            Object[] objs = list.get(i);
            String type = (String) objs[0];
            String gender = (String) objs[1];
            int num = ((BigInteger) objs[2]).intValue();
            if ("1".equals(type)) {
                maps.get("小学").put(gender, num);
            } else if ("2".equals(type)) {
                maps.get("初中").put(gender, num);
            } else if ("3".equals(type)) {
                maps.get("高中").put(gender, num);
            }
        }
        return maps;
    }


    public Map<String, Long> getFourTotalNum() {
        Long schoolNum = dao.findSchoolNum();
        Long clazzNum = dao.findClazzNum();
        Long teacherNum = dao.findTeacherNum();
        Long studentNum = dao.findStudentNum();
        Map map = new HashMap<String, Integer>();
        map.put("学校", schoolNum);
        map.put("班级", clazzNum);
        map.put("教师", teacherNum);
        map.put("学生", studentNum);
        return map;
    }

    public Map<String, Integer> getSchoolTypeNum() {
        Map<String, Integer> map = new HashMap<>();
        map.put("小学", 0);
        map.put("初中", 0);
        map.put("高中", 0);
        List<Object[]> list = dao.findSchoolTypeNum();
        list.forEach(objs -> {
            String type = (String) objs[0];
            int num = ((BigInteger) objs[1]).intValue();
            if ("1".equals(type)) {
                map.put("小学", num);
            } else if ("2".equals(type)) {
                map.put("初中", num);
            } else if ("3".equals(type)) {
                map.put("高中", num);
            }
        });
        return map;
    }

    private String[] townMap = {
            "东台市", "东台市", "东台市", "东台市", "东台市",
            "溱东镇", "时堰镇", "五烈镇", "梁垛镇", "安丰镇",
            "南沈灶镇", "富安镇", "唐洋镇", "新街镇", "许河镇",
            "三仓镇", "头灶镇", "弶港镇"
    };

    public Map<String, Integer[]> getTownSchoolInfo() {
        Map<String, Integer[]> maps = new HashMap<>();
        for (int i = 0; i < townMap.length; i++) {
            String townName = townMap[i];
            Integer[] schoolNum = {0, 0, 0};
            maps.put(townName, schoolNum);
        }
        List<Object[]> list = dao.findTownSchoolInfo();
        list.forEach(objs -> {
            int townId = (int) objs[0];
            String type = (String) objs[1];
            int index = Integer.parseInt(type) - 1;
            int num = ((BigInteger) objs[2]).intValue();
            if (index <= 2 && index >= 0) {
                if (townId <= 4) {
                    maps.get(townMap[townId])[index] += num;
                } else if (townId < townMap.length - 1) {
                    maps.get(townMap[townId])[index] = num;
                }
            }
        });
        return maps;
    }

    private String[] eduMap = {"其它", "大专", "本科", "硕士", "博士", "教授"};

    public Map<String, Integer> getTeacherEdu() {
        Map<String, Integer> map = new HashMap<>();
        List<Object[]> list1 = dao.findTeacherGenderNum();
        list1.forEach(objs -> {
            String gender = (String) objs[0];
            int num = ((BigInteger) objs[1]).intValue();
            map.put(gender, num);
        });

        for (String name : eduMap) {
            map.put(name, 0);
        }
        List<Object[]> list2 = dao.findTeacherEduNum();
        list2.forEach(objs -> {
            byte eduId = (Byte) objs[0];
            int num = ((BigInteger) objs[1]).intValue();
            map.put(eduMap[eduId], num);
        });
        return map;
    }

    public Map<String, Integer[]> getTeacherTitle() {
        Map<String, Integer[]> map = new HashMap<>();
        map.put("总体", new Integer[]{0, 0, 0, 0, 0, 0});
        map.put("男", new Integer[]{0, 0, 0, 0, 0, 0});
        map.put("女", new Integer[]{0, 0, 0, 0, 0, 0});
        List<Object[]> list = dao.findTeacherTitleNum();
        list.forEach(objs -> {
            byte titleId = (Byte) objs[0];
            String gender = (String) objs[1];
            int num = ((BigInteger) objs[2]).intValue();
            map.get(gender)[titleId] = num;
        });
        for (int i = 0; i < 6; i++) {
            map.get("总体")[i] = map.get("男")[i] + map.get("女")[i];
        }
        return map;
    }

    public void findMapQuery() {
        List<Map<String, Object>> list = dao.findMapQuery();
        list.forEach(map -> {
            byte proTitleId = (byte) map.get("职称");
            String gender = (String) map.get("性别");
            int num = ((BigInteger) map.get("数量")).intValue();
            System.out.println(proTitleId + gender + num);
        });
    }
}
