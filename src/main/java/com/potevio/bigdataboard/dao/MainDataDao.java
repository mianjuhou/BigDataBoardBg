package com.potevio.bigdataboard.dao;

import com.potevio.bigdataboard.pojo.MainBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface MainDataDao extends JpaRepository<MainBean, Integer>, JpaSpecificationExecutor<MainBean> {

    /**
     * 学校情况
     * 根据学校类型查询学生人数前4名
     */
    @Query(value = "select s.name,s.id,count(u.id) as snum from school s,cus_user u where s.id = u.schoolId and s.type = :schoolType and u.roles = '2' group by s.id order by snum desc limit 4", nativeQuery = true)
    List<Object[]> findSchoolStudentNum(@Param("schoolType") String schoolType);

    /**
     * 学校情况
     * 根据学校ID查询对应教师人数
     */
    @Query(value = "select schoolId,count(id) from cus_user where find_in_set('1',roles) and schoolId in (:ids) group by schoolId", nativeQuery = true)
    List<Object[]> findSchoolTeacherNum(@Param("ids") List<Integer> ids);

    /**
     * 学生信息-男女比例
     * 查询小学初中高中男女总数
     */
    @Query(value = "select s.type,c.gender,count(c.id) from cus_user c,school s where c.schoolId = s.id and roles = '2' group by s.type,c.gender", nativeQuery = true)
    List<Object[]> findStudentGenderNum();

    /**
     * 学校总数
     * select count(distinct schoolId) from cus_user where roles = '2'
     */
    @Query(value = "select count(distinct schoolId) from cus_user where roles = '2' ", nativeQuery = true)
    Long findSchoolNum();

    /**
     * 班级总数
     */
    @Query(value = "select count(distinct clazzId) from cus_user where roles = '2' and clazzId is not null and clazzId != '' ", nativeQuery = true)
    Long findClazzNum();

    /**
     * 教师总数
     */
    @Query(value = "select count(id) from cus_user where find_in_set('1',roles) ", nativeQuery = true)
    Long findTeacherNum();

    /**
     * 学生总数
     */
    @Query(value = "select count(*) from cus_user where roles = '2' ", nativeQuery = true)
    Long findStudentNum();

    /**
     * 办学层次
     * 各类学校数量
     */
    @Query(value = "select type,count(id) from school group by type ", nativeQuery = true)
    List<Object[]> findSchoolTypeNum();

    /**
     * 学校等级
     */
//    List<Object[]> findSchoolLeveNum();

    /**
     * 获取各镇各级学校的数量
     */
    @Query(value = "select townId,type,count(id) from school group by townId,type ", nativeQuery = true)
    List<Object[]> findTownSchoolInfo();

    /**
     * 教师信息-学历
     * 获取教师男女总数
     */
    @Query(value = "select gender,count(id) from cus_user where find_in_set('1',roles) group by gender ", nativeQuery = true)
    List<Object[]> findTeacherGenderNum();

    /**
     * 教师信息-学历
     * 获取教师各学历数量
     */
    @Query(value = "select eduRankId,count(id) from cus_user where find_in_set('1',roles) group by eduRankId ", nativeQuery = true)
    List<Object[]> findTeacherEduNum();

    /**
     * 教师职称
     * 获取各职称教书数量
     */
    @Query(value = "select proTitleId,gender,count(id) from cus_user where find_in_set('1',roles) group by proTitleId,gender ", nativeQuery = true)
    List<Object[]> findTeacherTitleNum();

    @Query(value = "select proTitleId,gender,count(id) num from cus_user where find_in_set('1',roles) group by proTitleId,gender ",
            nativeQuery = true)
    List<Map<String, Object>> findMapQuery();
}
