package com.school.demo.validator;

import com.school.demo.dto.CourseDTO;
import com.school.demo.entity.Role;
import com.school.demo.entity.Teacher;
import com.school.demo.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.io.InvalidObjectException;
import java.security.InvalidParameterException;
import java.util.Objects;

@AllArgsConstructor
@Component
public class Validator
{
  private        TeacherRepository teacherRepository;
  private final ModelMapper       mapper;

  public void validateObjectDoesExist(Object id) throws InvalidObjectException
  {
    if (id == null) {
      throw new InvalidObjectException("This object does not exist!");
    }
  }

  public void validateUsername(String username){
    if (Objects.isNull(username)){
      throw new InvalidParameterException("username cannot be null");
    }
  }

  public void validatePassword(String username){
    if (Objects.isNull(username)){
      throw new InvalidParameterException("password cannot be null");
    }
  }

  public void validateGrade(double grade)
  {
    if (grade > 6.00 || grade < 2.00) {
      throw new InvalidParameterException("The grade cannot be higher than 6.00 and lower than 2.00!");
    }
  }

  public void validateRole(Role role){
    if (Objects.isNull(role)){
      throw new InvalidParameterException("Role cannot be null");
    }
  }
}
