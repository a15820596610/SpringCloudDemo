package com.example.demo.dao.orcl;

import com.example.demo.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseDao {
    List<Course> queryCourse(Course dto);

    void insertCourse(Course dto);

    void updateCourse(Course dto);
}
