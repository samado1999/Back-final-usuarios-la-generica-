package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.List;

import com.bezkoder.spring.jwt.mongodb.repository.RoleRepository;
import com.bezkoder.spring.jwt.mongodb.models.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("role")
public class RolController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/createAll")
    public void createAll(@RequestBody List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @PostMapping("/create")
    public void create(@RequestBody Role rol) {
        roleRepository.save(rol);
    }

    @PutMapping("/update")
    public void update(@RequestBody Role rol) {
        roleRepository.save(rol);
    }

    @GetMapping("/list")
    public List<Role> list() {
        return roleRepository.findAll();
    }
}
