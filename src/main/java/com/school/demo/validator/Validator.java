package com.school.demo.validator;

import com.school.demo.entity.Role;
import com.school.demo.exception.InvalidArgumentException;
import com.school.demo.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.InvalidObjectException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class Validator {
    private final ModelMapper mapper;
    private TeacherRepository teacherRepository;

    public void validateObjectDoesExist(Object id) throws InvalidObjectException {
        if (id == null) {
            throw new InvalidObjectException("This object does not exist!");
        }
    }

    public void validateAddress(String username) {
        if (Objects.isNull(username)) {
            throw new InvalidArgumentException("address cannot be null");
        }
    }

    public void validateName(String username) {
        if (Objects.isNull(username)) {
            throw new InvalidArgumentException("name cannot be null");
        }
    }

    public void validateUsername(String username) {
        if (Objects.isNull(username)) {
            throw new InvalidArgumentException("username cannot be null");
        }
    }

    public void validatePassword(String username) {
        if (Objects.isNull(username)) {
            throw new InvalidArgumentException("password cannot be null");
        }
    }

    public void validateGrade(double grade) {
        if (grade > 6.00 || grade < 2.00) {
            throw new InvalidArgumentException("The grade cannot be higher than 6.00 and lower than 2.00!");
        }
    }

    public void validateRole(Role role) {
        if (Objects.isNull(role)) {
            throw new InvalidArgumentException("Role cannot be null");
        }
    }
}
