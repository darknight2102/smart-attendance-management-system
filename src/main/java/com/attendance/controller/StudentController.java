package com.attendance.controller;

import com.attendance.model.Student;
import com.attendance.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @GetMapping @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public List<Student> all() { return service.all(); }

    @GetMapping("/{id}") @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public Student get(@PathVariable Long id) { return service.get(id); }

    @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public Student create(@Valid @RequestBody Student student) { return service.create(student); }

    @PutMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student student) { return service.update(id, student); }

    @DeleteMapping("/{id}") @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
