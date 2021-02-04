package com.school.demo.services;

import com.school.demo.dto.DirectorDTO;
import com.school.demo.dto.TeacherDTO;
import com.school.demo.entity.*;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreateDirectorModel;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.roles;
import com.school.demo.validator.Validator;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.ParentDirectorView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final ModelMapper mapper;
    private final DirectorRepository directorRepository;
    private final SchoolService schoolService;
    private final Validator validator;

    @Override
    public DirectorDTO get(long directorId) {
        return convertToDTO(directorRepository.findById(directorId)
                .orElseThrow(() -> new NoSuchDataException(String.format("Director %s does not exists in records.", directorId)))
                , DirectorDTO.class);
    }

    @Override
    public DirectorDTO create(CreateDirectorModel model) {

        validator.validateRole(roles.ROLE_DIRECTOR);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        DirectorDTO director = new DirectorDTO();

        director.setFirstName(model.getFirstName());
        director.setLastName(model.getLastName());
        director.setPassword(model.getPassword());
        director.setUsername(model.getUsername());
        director.setRole(roles.ROLE_DIRECTOR);
        if (model.getSchool_id() != 0) {
            director.setSchool(mapper.map(schoolService.get(model.getSchool_id()), School.class));
        }

        directorRepository.save(mapper.map(director, Director.class));
        return director;
    }

    @Override
    public DirectorDTO edit(long id, CreateDirectorModel model) {

        validator.validateRole(roles.ROLE_DIRECTOR);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        DirectorDTO director = new DirectorDTO();

        director.setFirstName(model.getFirstName());
        director.setLastName(model.getLastName());
        director.setPassword(model.getPassword());
        director.setUsername(model.getUsername());
        director.setRole(roles.ROLE_DIRECTOR);
        if (model.getSchool_id() != 0) {
            director.setSchool(mapper.map(schoolService.get(model.getSchool_id()), School.class));
        }
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

        List<TeacherDTO> teacherDTOS = director.getSchool().getTeachers()
                .stream()
                .map(x -> convertToDTO(x, TeacherDTO.class))
                .collect(Collectors.toList());

        List<Set<Course>> setList = teacherDTOS
                .stream()
                .map(TeacherDTO::getCourses)
                .collect(Collectors.toList());

        List<Course> courses = new ArrayList<>();
        setList.forEach(courses::addAll);

        return courses.stream()
                .map(x -> mapper.map(x, CourseIdAndGradesView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherView> getAllTeachers(long directorId) {
        DirectorDTO director = this.get(directorId);

        return director.getSchool().getTeachers()
                .stream()
                .map(x -> convertToDTO(x, TeacherView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ParentDirectorView> getAllParents(long directorId) {
        DirectorDTO director = this.get(directorId);

        List<Set<Parent>> setOfParents = director.getSchool().getStudents()
                .stream()
                .map(Student::getParents)
                .collect(Collectors.toList());

        List<Parent> parents = new ArrayList<>();
        setOfParents.forEach(parents::addAll);

        return parents
                .stream()
                .map(parent -> convertToDTO(parent, ParentDirectorView.class))
                .collect(Collectors.toList());

    }

    private <T, S> S convertToDTO(T toBeConverted, Class<S> type) {
        return mapper.map(toBeConverted, type);
    }
}
