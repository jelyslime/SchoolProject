package com.school.demo.services;

import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.entity.Parent;
import com.school.demo.entity.Role;
import com.school.demo.entity.Student;
import com.school.demo.models.CreateParentModel;
import com.school.demo.repository.ParentRepository;
import com.school.demo.validator.Validator;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParentServiceImpl implements ParentService {
    private final ParentRepository repository;
    private final ModelMapper mapper;
    private final StudentServiceImpl service;
    private final Validator validator;

    @Override
    public ParentDTO get(long parentId) {
        return mapper.map(repository.findById(parentId).orElse(null), ParentDTO.class);
    }

    @Override
    public ParentDTO create(CreateParentModel model) {
        Role role = Role.PARENT;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        ParentDTO parent = new ParentDTO();

        parent.setFirstName(model.getFirstName());
        parent.setLastName(model.getLastName());
        parent.setPassword(model.getPassword());
        parent.setUsername(model.getUsername());
        parent.setKids(new HashSet<>());
        parent.setRole(role);

        repository.save(mapper.map(parent, Parent.class));
        return parent;
    }

    @Override
    public ParentDTO edit(long id, CreateParentModel model) {
        Role role = Role.PARENT;
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());

        ParentDTO parent = new ParentDTO();

        parent.setFirstName(model.getFirstName());
        parent.setLastName(model.getLastName());
        parent.setPassword(model.getPassword());
        parent.setUsername(model.getUsername());
        parent.setRole(role);

        parent.setId(id);

        repository.save(mapper.map(parent, Parent.class));
        return parent;
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
    public boolean addChild(long parentId, long StudentId) {
        ParentDTO parent = this.get(parentId);
        StudentDTO student = service.get(StudentId);
        if (Objects.isNull(parent) || Objects.isNull(student)) {
            return false;
        }

        parent.getKids().add(mapper.map(student, Student.class));

        repository.save(mapper.map(parent, Parent.class));
        return true;
    }

    @Override
    public boolean removeChild(long parentId, long StudentId) {
        ParentDTO parent = this.get(parentId);
        StudentDTO student = service.get(StudentId);
        if (Objects.isNull(parent) || Objects.isNull(student)) {
            return false;
        }

        parent.getKids().remove(mapper.map(student, Student.class));
        repository.save(mapper.map(parent, Parent.class));
        return true;
    }

    @Override
    public Map<String, List<CourseIdAndGradesView>> getAllGrades(long parentId) {
        ParentDTO parentDTO = mapper.map(repository.findById(parentId).orElse(new Parent()), ParentDTO.class);

        List<StudentDTO> kids = parentDTO.getKids()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        Map<String, List<CourseIdAndGradesView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()), service.getAllGrades(kid.getId()));
        }

        return kidsGrades;
    }

    @Override
    public Map<String, List<TeacherView>> getAllTeachers(long parentId) {
        ParentDTO parentDTO = mapper.map(repository.findById(parentId).orElse(new Parent()), ParentDTO.class);

        List<StudentDTO> kids = parentDTO.getKids()
                .stream()
                .map(student -> mapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());

        Map<String, List<TeacherView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()), service.getAllTeachers(kid.getId()));
        }

        return kidsGrades;
    }
}
