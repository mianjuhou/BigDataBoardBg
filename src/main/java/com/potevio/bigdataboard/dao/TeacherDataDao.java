package com.potevio.bigdataboard.dao;

import com.potevio.bigdataboard.pojo.TeacherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherDataDao extends JpaRepository<TeacherData, Long>, JpaSpecificationExecutor<TeacherData> {

    /**
     * 备授课次数
     */
    @Query(value = "select yearMonth,SUM(bLesson)+SUM(sLesson),SUM(bLesson),SUM(sLesson) from teacher_data GROUP BY yearMonth ORDER BY yearMonth desc limit 6", nativeQuery = true)
    List<Object[]> findBSKNum();

    /**
     * 备课top10
     */
    @Query(value = "select schoolName,SUM(bLesson) as bLessons from teacher_data GROUP BY schoolName ORDER BY bLessons DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findBKTop10();

    /**
     * 授课top10
     */
    @Query(value = "select schoolName,SUM(sLesson) as sLessons from teacher_data GROUP BY schoolName ORDER BY sLessons DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findSKTop10();

    /**
     * 教育资源上传总数,微课上传个数,习题上传个数
     */
    @Query(value = "select SUM(resources),SUM(classNum),SUM(exeNum) from teacher_data", nativeQuery = true)
    List<Object[]> findResourceNums();

    /**
     * 教学资源积累top10
     */
    @Query(value = "select schoolName,SUM(resources) as resourceAll from teacher_data GROUP BY schoolName ORDER BY resourceAll DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findResourceTop10();

    /**
     * 月度教学资源积累top10
     */
    @Query(value = "select schoolName,resources from teacher_data where yearMonth = :yearMonth ORDER BY resources DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findResourceMonthTop10(@Param("yearMonth") String yearMonth);

    /**
     * 学校上传教学资源统计
     */
    @Query(value = "select yearMonth,SUM(resources),SUM(classNum),SUM(exeNum) from teacher_data GROUP BY yearMonth ORDER BY yearMonth desc limit 6", nativeQuery = true)
    List<Object[]> findResourceMonth();

    /**
     * 学校活跃度统计
     */
    @Query(value = "select yearMonth,SUM(bClass),SUM(mClass),SUM(aClass) from teacher_data GROUP BY yearMonth ORDER BY yearMonth desc limit 6", nativeQuery = true)
    List<Object[]> findClassMonth();

    /**
     * 课前发布top10
     */
    @Query(value = "select schoolName,SUM(bClass) as bClasses from teacher_data GROUP BY schoolName ORDER BY bClasses DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findBClass();

    /**
     * 课中发布top10
     */
    @Query(value = "select schoolName,SUM(mClass) as mClasses from teacher_data GROUP BY schoolName ORDER BY mClasses DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findMClass();

    /**
     * 课后发布top10
     */
    @Query(value = "select schoolName,SUM(aClass) as aClasses from teacher_data GROUP BY schoolName ORDER BY aClasses DESC,schoolName asc LIMIT 10", nativeQuery = true)
    List<Object[]> findAClass();
}
