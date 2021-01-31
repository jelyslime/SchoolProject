package com.school.demo.services;

import com.school.demo.dto.CourseDTO;
import com.school.demo.dto.GradeDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Course;
import com.school.demo.entity.Grade;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.repository.GradeRepository;
import com.school.demo.repository.TeacherRepository;
import com.school.demo.validator.Validator;
import com.school.demo.views.GradeAsValueView;
import com.school.demo.views.PersonNamesView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService
{

  private final ModelMapper       mapper;
  private final TeacherRepository repository;
  private final GradeRepository   gradeRepository;
  private final Validator         validator;

  @Override
  public Map<Long, Map<PersonNamesView, List<GradeAsValueView>>> getAllStudentGrades(long teacherId)
  {
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

  @Override
  public void addGrade(long id, long course_id, double grade, long student_id) throws InvalidObjectException
  {
    validator.validateGrade(grade);
    Optional<Teacher> teacher = repository.findById(id);

    if (teacher.isPresent()) {
      CourseDTO course = mapper.map(teacher.get().getCourses()
          .stream()
          .filter(x -> x.getId() == course_id)
          .findFirst().orElse(new Course()), CourseDTO.class);
      if (course.equals(new CourseDTO())) {
        throw new InvalidObjectException("This teacher does not have such course.");
      }

      StudentDTO student = mapper.map(course.getStudents().stream()
          .filter(x -> x.getId() == student_id)
          .findFirst().orElse(new Student()), StudentDTO.class);

      if (student.equals(new StudentDTO())) {
        throw new InvalidObjectException("This course does not have such student.");
      }

      GradeDTO grade1 = new GradeDTO();
      grade1.setCourse(mapper.map(course, Course.class));
      grade1.setStudent(mapper.map(student, Student.class));
      grade1.setGrade(grade);

      //course.getGrades().add(mapper.map(grade1,Grade.class));
      gradeRepository.save(mapper.map(grade1, Grade.class));
    }
    else {
      throw new InvalidObjectException("This teacher does not exist!");
    }
  }

  @Override
  public void updateGrade(long id, long course_id, long grade_id, double grade) throws InvalidObjectException
  {
    validator.validateGrade(grade);
    Optional<Teacher> teacher = repository.findById(id);
    if (teacher.isPresent()) {

      CourseDTO course = Optional.ofNullable(mapper.map(teacher.get().getCourses()
          .stream()
          .filter(x -> x.getId() == course_id)
          .findFirst(), CourseDTO.class)).get();

      if (course.getId() != course_id) {
        throw new InvalidObjectException("This teacher does not have such course.");
      }

      GradeDTO gradeDTO = Optional.ofNullable(mapper.map(course.getGrades()
          .stream()
          .filter(x -> x.getId() == grade_id)
          .findFirst(), GradeDTO.class)).get();

      if (gradeDTO.equals(new GradeDTO())) {
        throw new InvalidObjectException("This course does not have such grade.");
      }

      gradeDTO.setGrade(grade);

      gradeRepository.save(mapper.map(gradeDTO, Grade.class));
    }
    else {
      throw new InvalidObjectException("This teacher does not exist!");
    }
  }

  @Override
  public void deleteGrade(long id, long course_id, long grade_id) throws InvalidObjectException
  {
    Teacher teacher = repository.findById(id).orElse(new Teacher());

    CourseDTO course = mapper.map(teacher.getCourses()
        .stream()
        .filter(x -> x.getId() == course_id)
        .findFirst().orElse(new Course()), CourseDTO.class);

    if (course.equals(new CourseDTO())) {
      throw new InvalidObjectException("This teacher does not have such course.");
    }

    GradeDTO gradeDTO = mapper.map(course.getGrades()
        .stream()
        .filter(x -> x.getId() == grade_id)
        .findFirst().orElse(new Grade()), GradeDTO.class);

    if (gradeDTO.equals(new GradeDTO())) {
      throw new InvalidObjectException("This course does not have such grade.");
    }

    gradeRepository.delete(mapper.map(gradeDTO, Grade.class));
  }

}
