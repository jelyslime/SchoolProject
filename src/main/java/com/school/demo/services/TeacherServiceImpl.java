package com.school.demo.services;

import com.school.demo.controllers.SchoolController;
import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.GradeDTO;
import com.school.demo.dto.SchoolDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Grade;
import com.school.demo.entity.Role;
import com.school.demo.entity.School;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.repository.GradeRepository;
import com.school.demo.repository.TeacherRepository;
import com.school.demo.validator.Validator;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
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
    private final GradeRepository gradeRepository;
    private final Validator validator;


    @Override
    public TeacherDTO get(long teacherId) {
        return mapper.map(repository.findById(teacherId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Teacher %s does not exists in records.", teacherId)))
                , TeacherDTO.class);
    }

    @Override
    public TeacherDTO create(CreatePersonModel model) {
        Role role = Role.TEACHER;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setCourses(new HashSet<>());
        teacherDTO.setCourses(new HashSet<>());
        teacherDTO.setSchool(null);

        teacherDTO.setFirstName(model.getFirstName());
        teacherDTO.setLastName(model.getLastName());
        teacherDTO.setUsername(model.getUsername());
        teacherDTO.setPassword(model.getPassword());

        Teacher entity = mapper.map(teacherDTO,Teacher.class);
        return mapper.map(repository.save(entity),TeacherDTO.class);
    }

    @Override
    public TeacherDTO edit(long id, CreatePersonModel model) {
        Role role = Role.TEACHER;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setFirstName(model.getFirstName());
        teacherDTO.setLastName(model.getLastName());
        teacherDTO.setUsername(model.getUsername());
        teacherDTO.setPassword(model.getPassword());
        teacherDTO.setId(id);

        Teacher entity = mapper.map(teacherDTO,Teacher.class);
        return mapper.map(repository.save(entity),TeacherDTO.class);
    }

    @Override
    public boolean delete(long id) {
        boolean result = repository.existsById(id);
        if (!result) {
            return false;
        }
        repository.deleteById(id);
        result = repository.existsById(id);
        return !result;
    }


    @Override
    public Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId) {
        TeacherDTO teacher = this.get(teacherId);

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
                List<GradeAsValueView> value = studentListEntry.getValue();
                buffer.put(key, value);
            }


            courseIdStudentGradesMap.put(course.getId(), buffer);
        }

        return courseIdStudentGradesMap;
    }


@Override
    public boolean removeSchool(long id){
        TeacherDTO teacher = this.get(id);

        teacher.setSchool(null);

        repository.saveAndFlush(mapper.map(teacher,Teacher.class));
        return true;
    }


    @Override
    public Grade addGrade(long id, long course_id, double grade, long student_id) {
        validator.validateGrade(grade);
        TeacherDTO teacher = this.get(id);

        CourseDTO course = mapper.map(teacher.getCourses()
                        .stream()
                        .filter(x -> x.getId() == course_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Teacher %s does not have course %s.", id, course_id)))
                , CourseDTO.class);

        StudentDTO student = mapper.map(course.getStudents().stream()
                        .filter(x -> x.getId() == student_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Student %s not found in course %s.", student_id, course_id)))
                , StudentDTO.class);

        GradeDTO grade1 = new GradeDTO();
        grade1.setCourse(mapper.map(course, Course.class));
        grade1.setStudent(mapper.map(student, Student.class));
        grade1.setGrade(grade);

        return gradeRepository.save(mapper.map(grade1, Grade.class));
    }

    @Override
    public Grade updateGrade(long id, long course_id, long grade_id, double grade) {
        validator.validateGrade(grade);

        TeacherDTO teacher = this.get(id);

        CourseDTO course = mapper.map(teacher.getCourses()
                        .stream()
                        .filter(x -> x.getId() == course_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Teacher %s does not have course %s.", id, course_id)))
                , CourseDTO.class);

        GradeDTO gradeDTO = mapper.map(course.getGrades()
                        .stream()
                        .filter(x -> x.getId() == grade_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Course %s does not have grade with id %s.", course_id, grade_id)))
                , GradeDTO.class);

        gradeDTO.setGrade(grade);

        return gradeRepository.save(mapper.map(gradeDTO, Grade.class));
    }

    @Override
    public void deleteGrade(long id, long course_id, long grade_id) {
        Teacher teacher = repository.findById(id).orElse(new Teacher());

        CourseDTO course = mapper.map(teacher.getCourses()
                        .stream()
                        .filter(x -> x.getId() == course_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Teacher %s does not have course with id %s.", id, course_id)))
                , CourseDTO.class);

        GradeDTO gradeDTO = mapper.map(course.getGrades()
                        .stream()
                        .filter(x -> x.getId() == grade_id)
                        .findFirst().orElseThrow(() ->
                                new NoSuchDataException(String.format("Course %s does not have grade with id %s.", course_id, grade_id)))
                , GradeDTO.class);

        gradeRepository.delete(mapper.map(gradeDTO, Grade.class));
    }

}
