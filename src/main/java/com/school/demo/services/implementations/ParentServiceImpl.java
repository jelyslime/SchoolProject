package com.school.demo.services.implementations;

import com.school.demo.converter.GenericConverter;
import com.school.demo.data.entity.Parent;
import com.school.demo.data.entity.Role;
import com.school.demo.data.entity.Student;
import com.school.demo.data.repository.ParentRepository;
import com.school.demo.dto.ParentDTO;
import com.school.demo.dto.StudentDTO;
import com.school.demo.exception.NoSuchDataException;
import com.school.demo.models.CreatePersonModel;
import com.school.demo.services.ParentService;
import com.school.demo.validator.Validator;
import com.school.demo.views.CourseIdAndGradesView;
import com.school.demo.views.TeacherView;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final GenericConverter converter;
    private final Validator validator;

    private final StudentServiceImpl service;

    private final ParentRepository repository;

    @Override
    public ParentDTO get(long parentId) {
        return converter.convert(repository.findById(parentId)
                        .orElseThrow(() -> new NoSuchDataException(String.format("Parent %s does not exists in records.", parentId)))
                , ParentDTO.class);
    }

    @Override
    public ParentDTO create(CreatePersonModel model) {
        Role role = Role.PARENT;
        validateModel(model, role);

        ParentDTO parent = populateParentDTO(new ParentDTO(), model);
        parent.setKids(new HashSet<>());
        parent.setRole(role);

        repository.save(converter.convert(parent, Parent.class));
        return parent;
    }


    @Override
    public ParentDTO edit(long id, CreatePersonModel model) {
        Role role = Role.PARENT;
        validateModel(model, role);

        ParentDTO parent = populateParentDTO(this.get(id), model);

        repository.save(converter.convert(parent, Parent.class));
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

        parent.getKids().add(converter.convert(student, Student.class));


        Parent ent = converter.convert(parent, Parent.class);


        ent.getKids().add(converter.convert(student, Student.class));

        repository.saveAndFlush(ent);
        return true;
    }

    @Override
    public boolean removeChild(long parentId, long StudentId) {
        ParentDTO parent = this.get(parentId);
        StudentDTO student = service.get(StudentId);

        boolean result = parent.getKids().remove(converter.convert(student, Student.class));
        repository.save(converter.convert(parent, Parent.class));
        return result;
    }

    @Override
    public Map<String, List<CourseIdAndGradesView>> getAllGrades(long parentId) {
        ParentDTO parentDTO = this.get(parentId);

        List<StudentDTO> kids = converter.convertList(parentDTO.getKids(), StudentDTO.class);

        Map<String, List<CourseIdAndGradesView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()),
                    service.getAllGrades(kid.getId()));
        }

        return kidsGrades;
    }

    @Override
    public Map<String, List<TeacherView>> getAllTeachers(long parentId) {
        ParentDTO parentDTO = this.get(parentId);

        List<StudentDTO> kids = converter.convertList(parentDTO.getKids(), StudentDTO.class);

        Map<String, List<TeacherView>> kidsGrades = new HashMap<>();

        for (StudentDTO kid : kids) {
            kidsGrades.put(String.format("%s %s", kid.getFirstName(), kid.getLastName()),
                    service.getAllTeachers(kid.getId()));
        }

        return kidsGrades;
    }

    private ParentDTO populateParentDTO(ParentDTO parent, CreatePersonModel model) {


        parent.setFirstName(model.getFirstName());
        parent.setLastName(model.getLastName());
        parent.setPassword(model.getPassword());
        parent.setUsername(model.getUsername());
        return parent;
    }

    private void validateModel(CreatePersonModel model, Role role) {
        validator.validateRole(role);
        validator.validateUsername(model.getUsername());
        validator.validatePassword(model.getPassword());
    }
}
