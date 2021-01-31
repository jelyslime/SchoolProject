package com.school.demo.services;

import com.school.demo.entity.Course;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.repository.TeacherRepository;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final ModelMapper mapper;
    private final TeacherRepository repository;

    public Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId) {
        Teacher teacher = repository.findById(teacherId).orElse(new Teacher());

        Set<Course> courses = teacher.getCourses();

        Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> courseIdStudentGradesMap = new HashMap<>();

        for (Course course : courses) {

            Map<Student, List<GradeAsValueView>> studentGradesForCourse = course.getStudents()
                    .stream()
                    .collect(Collectors.toMap(Function.identity(), student -> student.getGrades()
                            .stream()
                            .filter(grade -> grade.getCourse().equals(course))
                            .map(grade -> mapper.map(grade, GradeAsValueView.class))
                            .collect(Collectors.toList()))
                    );

            Set<Map.Entry<Student, List<GradeAsValueView>>> entrySet = studentGradesForCourse.entrySet();
            Map<PersonNamesView, List<GradeAsValueView>> buffer = new HashMap<>();

            for (Map.Entry<Student, List<GradeAsValueView>> studentListEntry : entrySet) {
                PersonNamesView key = mapper.map(studentListEntry.getKey(), PersonNamesView.class);
                System.out.println(key.getFirstName() + " NAMEEEEEEEEEEEEE");
                List<GradeAsValueView> value = studentListEntry.getValue();
                buffer.put(key, value);
            }

            System.out.println("BUFFER SIZE" + buffer.size());

            courseIdStudentGradesMap.put(course.getId(), buffer);
        }

        return courseIdStudentGradesMap;
    }

    public void addGrade(long id, long course_id, double grade, long student_id){
        Teacher teacher = repository.findById(id).orElse(new Teacher());


    }

}
