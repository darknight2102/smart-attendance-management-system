package com.attendance.service;

import com.attendance.model.Student;
import com.attendance.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository repo) { this.repo = repo; }
    public List<Student> all() { return repo.findAll(); }
    public Student get(Long id) { return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found")); }
    public Student create(Student student) {
        if (repo.existsByRollNumber(student.getRollNumber())) throw new ResponseStatusException(HttpStatus.CONFLICT, "Roll number already exists");
        return repo.save(student);
    }
    public Student update(Long id, Student input) {
        Student s = get(id);
        s.setName(input.getName()); s.setRollNumber(input.getRollNumber());
        s.setEmail(input.getEmail()); s.setCourse(input.getCourse());
        return repo.save(s);
    }
    public void delete(Long id) { repo.delete(get(id)); }
}
