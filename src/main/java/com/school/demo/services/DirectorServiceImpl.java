package com.school.demo.services;

import com.school.demo.converter.GenericConverter;
import com.school.demo.dto.DirectorDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.*;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateDirectorModel;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.validator.Validator;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.ParentDirectorView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final GenericConverter converter;
    private final ModelMapper mapper;
    private final DirectorRepository directorRepository;
    private final SchoolService schoolService;
    private final Validator validator;

    @Override
    public DirectorDTO get(long directorId) {
        return converter.convert(directorRepository.findById(directorId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Director %s does not exists in records.", directorId)))
                , DirectorDTO.class);
    }

    @Override
    public DirectorDTO create(CreateDirectorModel model) {
        Role role = Role.DIRECTOR;
        validateModel(model, role);

        DirectorDTO director = new DirectorDTO();

        populateDirector(model, role, director);

        directorRepository.save(mapper.map(director, Director.class));
        return director;
    }


    @Override
    public DirectorDTO edit(long id, CreateDirectorModel model) {
        Role role = Role.DIRECTOR;
        validateModel(model, role);

        DirectorDTO director = new DirectorDTO();

        populateDirector(model, role, director);
        director.setId(id);

        directorRepository.save(mapper.map(director, Director.class));
        return director;
    }

    @Override
    public boolean delete(long id) {
        boolean result = directorRepository.existsById(id);
        if (!result) {
            return false;
        }
        directorRepository.deleteById(id);
        result = directorRepository.existsById(id);
        return !result;
    }

    @Override
    public List<CourseIdAndGradesView> getAllCoursesAndAllGrades(long directorId) {
        DirectorDTO director = this.get(directorId);

        List<TeacherDTO> teacherDTOS = getTeacherDTOS(director);

        List<Set<Course>> setList = getSetOfCourses(teacherDTOS);

        List<Course> courses = new ArrayList<>();
        setList.forEach(courses::addAll);

        return converter.convertList(courses, CourseIdAndGradesView.class);
    }


    @Override
    public List<TeacherView> getAllTeachers(long directorId) {
        DirectorDTO director = this.get(directorId);

        return converter.convertList(director.getSchool().getTeachers(), TeacherView.class);
    }

    @Override
    public List<ParentDirectorView> getAllParents(long directorId) {
        DirectorDTO director = this.get(directorId);

        List<Set<Parent>> setOfParents = getSetOfParents(director);

        List<Parent> parents = new ArrayList<>();
        setOfParents.forEach(parents::addAll);

        return converter.convertList(parents, ParentDirectorView.class);
    }

    private List<Set<Parent>> getSetOfParents(DirectorDTO director) {
        return director.getSchool().getStudents()
                .stream()
                .map(Student::getParents)
                .collect(Collectors.toList());

    }

    private void populateDirector(CreateDirectorModel model, Role role, DirectorDTO director) {
        director.setFirstName(model.getFirstName());
        director.setLastName(model.getLastName());
        director.setPassword(model.getPassword());
        director.setUsername(model.getUsername());
        director.setRole(role);
        if (model.getSchool_id() != 0) {
            director.setSchool(mapper.map(schoolService.get(model.getSchool_id()), School.class));
        }
    }

    private void validateModel(CreateDirectorModel model, Role role) {
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());
    }

    private List<Set<Course>> getSetOfCourses(List<TeacherDTO> teacherDTOS) {
        //using .flatmap in stream brakes the whole container
        return teacherDTOS
                .stream()
                .map(TeacherDTO::getCourses)
                .collect(Collectors.toList());

    }

    private List<TeacherDTO> getTeacherDTOS(DirectorDTO director) {
        return converter.convertList(director.getSchool().getTeachers(), TeacherDTO.class);
    }

}
