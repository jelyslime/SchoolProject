package com.school.demo.services;

import com.school.demo.dto.ParentDTO;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;

import java.util.List;
import java.util.Map;

public interface ParentService {

    ParentDTO get(long parentId);

    ParentDTO create(CreatePersonModel model);

    ParentDTO edit(long id, CreatePersonModel model);

    boolean delete(long id);

    boolean addChild(long parentId, long StudentId);

    boolean removeChild(long parentId, long StudentId);

    Map<String, List<CourseIdAndGradesView>> getAllGrades(long parentId);

    Map<String, List<TeacherView>> getAllTeachers(long parentId);
}
