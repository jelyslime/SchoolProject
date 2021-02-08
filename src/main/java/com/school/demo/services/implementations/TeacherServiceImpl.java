package com.school.demo.services.implementations;

import com.school.demo.converter.GenericConverter;
import com.school.demo.data.entity.Course;
import com.school.demo.data.entity.Grade;
import com.school.demo.data.entity.Role;
import com.school.demo.data.entity.Student;
import com.school.demo.data.entity.Teacher;
import com.school.demo.data.repository.GradeRepository;
import com.school.demo.data.repository.TeacherRepository;
import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.GradeDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.TeacherService;
import com.school.demo.validator.Validator;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
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

    private final GenericConverter converter;
    private final Validator validator;

    private final TeacherRepository repository;
    private final GradeRepository gradeRepository;


    @Override
    public TeacherDTO get(long teacherId) {
        return converter.convert(repository.findById(teacherId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Teacher %s does not exists in records.", teacherId)))
                , TeacherDTO.class);
    }

    @Override
    public TeacherDTO create(CreatePersonModel model) {
        Role role = Role.TEACHER;
        validateModel(model, role);

        TeacherDTO teacherDTO = initialPopulationTeacherDTO(model);
        teacherDTO.setRole(role);

        Teacher entity = converter.convert(teacherDTO, Teacher.class);
        return converter.convert(repository.save(entity), TeacherDTO.class);
    }


    @Override
    public TeacherDTO edit(long id, CreatePersonModel model) {
        Role role = Role.TEACHER;
        validateModel(model, role);

        TeacherDTO teacherDTO = this.get(id);

        populateTeacherDTO(model, teacherDTO);

        Teacher entity = converter.convert(teacherDTO, Teacher.class);
        return converter.convert(repository.save(entity), TeacherDTO.class);
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

        return getCourseAndGrades(courses);
    }


    @Override
    public boolean removeSchool(long id) {
        TeacherDTO teacher = this.get(id);

        teacher.setSchool(null);

        repository.saveAndFlush(converter.convert(teacher, Teacher.class));
        return true;
    }


    @Override
    public Grade addGrade(long id, long course_id, double grade, long student_id) {
        validator.validateGrade(grade);
        TeacherDTO teacher = this.get(id);

        CourseDTO course = getCourseDTO(id, course_id, teacher.getCourses());

        StudentDTO student = getStudentDTO(course_id, student_id, course);

        GradeDTO gradeAsObject = createNewGradeDTO(grade, course, student);

        return gradeRepository.save(converter.convert(gradeAsObject, Grade.class));
    }

    @Override
    public Grade updateGrade(long id, long course_id, long grade_id, double grade) {
        validator.validateGrade(grade);

        TeacherDTO teacher = this.get(id);

        CourseDTO course = getCourseDTO(id, course_id, teacher.getCourses());

        GradeDTO gradeDTO = getGradeDTO(course_id, grade_id, course);

        gradeDTO.setGrade(grade);

        return gradeRepository.save(converter.convert(gradeDTO, Grade.class));
    }

    @Override
    public void deleteGrade(long id, long course_id, long grade_id) {
        Teacher teacher = repository.findById(id).orElse(new Teacher());

        CourseDTO course = getCourseDTO(id, course_id, teacher.getCourses());

        GradeDTO gradeDTO = getGradeDTO(course_id, grade_id, course);

        gradeRepository.delete(converter.convert(gradeDTO, Grade.class));
    }

    private void populateTeacherDTO(CreatePersonModel model, TeacherDTO teacherDTO) {
        teacherDTO.setFirstName(model.getFirstName());
        teacherDTO.setLastName(model.getLastName());
        teacherDTO.setUsername(model.getUsername());
        teacherDTO.setPassword(model.getPassword());
    }

    private TeacherDTO initialPopulationTeacherDTO(CreatePersonModel model) {
        TeacherDTO teacherDTO = new TeacherDTO();

        teacherDTO.setCourses(new HashSet<>());
        teacherDTO.setCourses(new HashSet<>());
        teacherDTO.setSchool(null);

        populateTeacherDTO(model, teacherDTO);
        return teacherDTO;
    }

    private void validateModel(CreatePersonModel model, Role role) {
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());
    }

    private GradeDTO getGradeDTO(long course_id, long grade_id, CourseDTO course) {
        return converter.convert(course.getGrades()
                        .stream()
                        .filter(x -> x.getId() == grade_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Course %s does not have grade with id %s.", course_id, grade_id)))
                , GradeDTO.class);
    }

    private CourseDTO getCourseDTO(long id, long course_id, Set<Course> courses) {
        return converter.convert(courses
                        .stream()
                        .filter(x -> x.getId() == course_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Teacher %s does not have course with id %s.", id, course_id)))
                , CourseDTO.class);
    }

    private StudentDTO getStudentDTO(long course_id, long student_id, CourseDTO course) {
        return converter.convert(course.getStudents().stream()
                        .filter(x -> x.getId() == student_id)
                        .findFirst()
                        .orElseThrow(() ->
                                new NoSuchDataException(String.format("Student %s not found in course %s.", student_id, course_id)))
                , StudentDTO.class);
    }

    private GradeDTO createNewGradeDTO(double grade, CourseDTO course, StudentDTO student) {
        GradeDTO grade1 = new GradeDTO();
        grade1.setCourse(converter.convert(course, Course.class));
        grade1.setStudent(converter.convert(student, Student.class));
        grade1.setGrade(grade);
        return grade1;
    }

    private Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getCourseAndGrades(Set<Course> courses) {
        Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> courseIdStudentGradesMap = new HashMap<>();

        for (Course course : courses) {

            Map<Student, List<GradeAsValueView>> studentGradesForCourse = getStudentAllGradesPerSubject(course);

            Set<Map.Entry<Student, List<GradeAsValueView>>> entrySet = studentGradesForCourse.entrySet();

            Map<PersonNamesView, List<GradeAsValueView>> buffer = bufferGradesPerStudent(entrySet);

            courseIdStudentGradesMap.put(course.getId(), buffer);
        }
        return courseIdStudentGradesMap;
    }

    private Map<PersonNamesView, List<GradeAsValueView>> bufferGradesPerStudent(Set<Map.Entry<Student, List<GradeAsValueView>>> entrySet) {
        Map<PersonNamesView, List<GradeAsValueView>> buffer = new HashMap<>();

        for (Map.Entry<Student, List<GradeAsValueView>> studentListEntry : entrySet) {
            PersonNamesView key = converter.convert(studentListEntry.getKey(), PersonNamesView.class);
            List<GradeAsValueView> value = studentListEntry.getValue();
            buffer.put(key, value);
        }
        return buffer;
    }

    private Map<Student, List<GradeAsValueView>> getStudentAllGradesPerSubject(Course course) {
        return course.getStudents()
                .stream()
                .collect(Collectors.toMap(Function.identity(), student -> student.getGrades()
                        .stream()
                        .filter(grade -> grade.getCourse().equals(course))
                        .map(grade -> converter.convert(grade, GradeAsValueView.class))
                        .collect(Collectors.toList()))
                );
    }
}
