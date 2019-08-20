package com.potevio.bigdataboard.service;

import com.potevio.bigdataboard.dao.TeacherDataDao;
import com.potevio.bigdataboard.ws.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TeacherDataService {

    @Autowired
    private TeacherDataDao dao;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

    private HashMap<String, List> getNameNum(List<Object[]> list) {
        List<String> names = new ArrayList<>();
        List<Long> nums = new ArrayList<>();
        for (Object[] objs : list) {
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                if (i == 0) {
                    names.add((String) obj);
                } else if (i == 1) {
                    BigDecimal bd = (BigDecimal) obj;
                    nums.add(bd.longValue());
                }
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("names", names);
        map.put("nums", nums);
        return map;
    }

    private HashMap<String, List> getNameNumRev(List<Object[]> list) {
        List<String> names = new ArrayList<>();
        List<Long> nums = new ArrayList<>();
        for (int j = list.size() - 1; j >= 0; j--) {
            Object[] objs = list.get(j);
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                if (i == 0) {
                    names.add((String) obj);
                } else if (i == 1) {
                    BigDecimal bd = (BigDecimal) obj;
                    nums.add(bd.longValue());
                }
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("names", names);
        map.put("nums", nums);
        return map;
    }

    private HashMap<String, List> getNameNum3(List<Object[]> list) {
        List<String> names = new ArrayList<>();
        List<Long> nums1 = new ArrayList<>();
        List<Long> nums2 = new ArrayList<>();
        List<Long> nums3 = new ArrayList<>();
        for (int j = list.size() - 1; j >= 0; j--) {
            Object[] objs = list.get(j);
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                if (i == 0) {
                    names.add((String) obj);
                } else if (i == 1) {
                    BigDecimal bd = (BigDecimal) obj;
                    nums1.add(bd.longValue());
                } else if (i == 2) {
                    BigDecimal bd = (BigDecimal) obj;
                    nums2.add(bd.longValue());
                } else if (i == 3) {
                    BigDecimal bd = (BigDecimal) obj;
                    nums3.add(bd.longValue());
                }
            }
        }
        HashMap<String, List> map = new HashMap<>();
        map.put("names", names);
        map.put("num1", nums1);
        map.put("num2", nums2);
        map.put("num3", nums3);
        return map;
    }

    public void sendInfo(String msg) {
        try {
            MyWebSocket.sendInfo(msg);
            System.out.println("WS发送" + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean mapEquals(Map<String, List> map1, Map<String, List> map2) {
        Set<String> set1 = map1.keySet();
        Set<String> set2 = map2.keySet();
        if (!set1.equals(set2)) {
            return false;
        }
        for (String key : set1) {
            if (!map1.get(key).equals(map2.get(key))) {
                return false;
            }
        }
        return true;
    }

    private Map<Integer, Map> maps = new HashMap<>();

    public void syncFunc(Integer key) {


        HashMap<String, List> map = null;
        List<Object[]> list = null;
        switch (key) {
            case 1:
                list = dao.findBSKNum();
                map = getNameNum3(list);
                break;
            case 2:
                list = dao.findBKTop10();
                map = getNameNumRev(list);
                break;
            case 3:
                list = dao.findSKTop10();
                map = getNameNumRev(list);
                break;
            case 4: {
                list = dao.findResourceNums();
                Object[] objs = list.get(0);
                HashMap<String, Long> map4 = new HashMap<>();
                BigDecimal bg0 = (BigDecimal) objs[0];
                map4.put("resource", bg0.longValue());
                BigDecimal bg1 = (BigDecimal) objs[1];
                map4.put("class", bg1.longValue());
                BigDecimal bg2 = (BigDecimal) objs[2];
                map4.put("exe", bg2.longValue());
                if (maps.get(key) == null) {
                    maps.put(key, map4);
                    sendInfo(String.valueOf(key));
                } else {
                    if (!map4.equals(maps.get(key))) {
                        maps.put(key, map4);
                        sendInfo(String.valueOf(key));
                    }
                }
                return;
            }
            case 5:
                list = dao.findResourceTop10();
                map = getNameNum(list);
                break;
            case 6: {
                String yearMonth = dateFormat.format(new Date());
                list = dao.findResourceMonthTop10(yearMonth);
                List<String> names = new ArrayList<>();
                List<Integer> nums = new ArrayList<>();
                for (Object[] objs : list) {
                    for (int i = 0; i < objs.length; i++) {
                        Object obj = objs[i];
                        if (i == 0) {
                            names.add((String) obj);
                        } else if (i == 1) {
                            nums.add((Integer) obj);
                        }
                    }
                }
                map = new HashMap<>();
                map.put("names", names);
                map.put("nums", nums);
            }
            break;
            case 7:
                list = dao.findResourceMonth();
                map = getNameNum3(list);
                break;
            case 8:
                list = dao.findClassMonth();
                map = getNameNum3(list);
                break;
            case 9:
                list = dao.findBClass();
                map = getNameNumRev(list);
                break;
            case 10:
                list = dao.findMClass();
                map = getNameNumRev(list);
                break;
            case 11:
                list = dao.findAClass();
                map = getNameNumRev(list);
                break;
        }
        if (maps.get(key) == null) {
            maps.put(key, map);
            sendInfo(String.valueOf(key));
        } else {
            //判断是否有变化
            if (!mapEquals(map, maps.get(key))) {
                maps.put(key, map);
                sendInfo(String.valueOf(key));
            }
        }
    }

    //1
    public Map<String, List> findBSKNum() {
        Integer key = 1;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //2
    public Map<String, List> findBKTop10() {
        Integer key = 2;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //3
    public Map<String, List> findSKTop10() {
        Integer key = 3;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //4
    public Map<String, Long> findResourceNums() {
        Integer key = 4;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //5
    public Map<String, List> findResourceTop10() {
        Integer key = 5;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //6
    public Map<String, List> findResourceMonthTop10() {
        Integer key = 6;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //7
    public Map<String, List> findResourceMonth() {
        Integer key = 7;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //8
    public Map<String, List> findClassMonth() {
        Integer key = 8;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //9
    public Map<String, List> findBClass() {
        Integer key = 9;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //10
    public Map<String, List> findMClass() {
        Integer key = 10;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }

    //11
    public Map<String, List> findAClass() {
        Integer key = 11;
        if (maps.get(key) != null) {
            return maps.get(key);
        } else {
            syncFunc(key);
            return maps.get(key);
        }
    }
}
