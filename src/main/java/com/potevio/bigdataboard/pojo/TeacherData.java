package com.potevio.bigdataboard.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "teacher_data")
@Table(name = "clazz")
@Data
public class TeacherData {

    @Id
    private Integer id;
//    @Id
//    private Long id;
//    private Long schoolId;
//    private String schoolName;
//    private Long bLesson;
//    private Long sLesson;
//    private String yearMonth;
//    private Long bClass;
//    private Long mClass;
//    private Long aClass;
//    private Long resources;
//    private Long classNum;
//    private Long exeNum;

}
