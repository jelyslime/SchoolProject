package com.school.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "school")
public class School extends BaseEntity {

    @NotBlank
    @Size(min = 5, max = 20, message = "Min 5, Max 20")
    private String name;

    @NotBlank
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private Director director;

    @OneToMany(mappedBy = "school")
    @JsonIgnoreProperties("school")
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "school")
    @JsonIgnoreProperties("school")
    private List<Student> students;


}
