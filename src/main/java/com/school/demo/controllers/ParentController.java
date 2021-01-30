package com.school.demo.controllers;

import com.school.demo.dto.ParentDTO;
import com.school.demo.entity.Parent;
import com.school.demo.repository.ParentRepository;
import com.school.demo.views.ParentView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParentController {
    @Autowired
    ParentRepository repository;

    @Autowired
    private ModelMapper mapper;

    @GetMapping("parent/{id}")
    ParentView getStudent(@PathVariable("id") long id) {
        Parent parent = repository.findById(id).orElse(new Parent());
        ParentDTO dto = mapper.map(parent, ParentDTO.class);
        ParentView view = mapper.map(dto, ParentView.class);

        return view;
    }
}
