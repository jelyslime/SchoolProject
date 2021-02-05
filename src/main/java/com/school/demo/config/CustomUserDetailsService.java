package com.school.demo.config;

import com.school.demo.entity.Director;
import com.school.demo.entity.Parent;
import com.school.demo.entity.Student;
import com.school.demo.entity.Teacher;
import com.school.demo.models.CustomUserDetails;
import com.school.demo.models.UserModel;
import com.school.demo.repository.DirectorRepository;
import com.school.demo.repository.ParentRepository;
import com.school.demo.repository.StudentRepository;
import com.school.demo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Date: 2/4/2021 Time: 5:22 PM
 * <p>
 * TODO: WRITE THE DESCRIPTION HERE
 *
 * @author Vladislav_Zlatanov
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    ParentRepository parentRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Director user = directorRepository.findByUsernameNative(username);

        if (user != null) {
            UserModel model = new UserModel(user.getUsername(),user.getPassword(),true,user.getRole());

            return new CustomUserDetails(model);
        }

        Parent parent = parentRepository.findByUsername(username);

        if (parent != null) {
            UserModel model = new UserModel(parent.getUsername(),parent.getPassword(),true,parent.getRole());

            return new CustomUserDetails(model);
        }

        Student student = studentRepository.findByUsername(username);

        if (student != null) {
            UserModel model = new UserModel(student.getUsername(),student.getPassword(),true,student.getRole());

            return new CustomUserDetails(model);
        }

        Teacher teacher = teacherRepository.findByUsername(username);

        if (teacher != null) {
            UserModel model = new UserModel(teacher.getUsername(),teacher.getPassword(),true,teacher.getRole());

            return new CustomUserDetails(model);
        }

        throw new UsernameNotFoundException("User with " + username + " not found.");

    }
}
