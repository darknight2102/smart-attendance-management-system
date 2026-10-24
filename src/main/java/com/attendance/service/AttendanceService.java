package com.attendance.service;

import com.attendance.model.*;
import com.attendance.repository.AttendanceRepository;
import com.attendance.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepo;
    private final StudentRepository studentRepo;
    public AttendanceService(AttendanceRepository attendanceRepo, StudentRepository studentRepo) {
        this.attendanceRepo = attendanceRepo; this.studentRepo = studentRepo;
    }
    public Attendance mark(Long studentId, LocalDate date, String subject, AttendanceStatus status) {
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        if (attendanceRepo.existsByStudentIdAndAttendanceDateAndSubject(studentId, date, subject))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Attendance already marked for this student, date and subject");
        return attendanceRepo.save(new Attendance(student, date, subject, status));
    }
    public List<Attendance> all() { return attendanceRepo.findAll(); }
    public List<Attendance> byDate(LocalDate date) { return attendanceRepo.findByAttendanceDate(date); }
    public List<Attendance> byStudent(Long studentId) {
        if (!studentRepo.existsById(studentId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        return attendanceRepo.findByStudentIdOrderByAttendanceDateDesc(studentId);
    }
    public Map<String, Object> report(Long studentId) {
        Student student = studentRepo.findById(studentId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        long total = attendanceRepo.countByStudentId(studentId);
        long present = attendanceRepo.countByStudentIdAndStatus(studentId, AttendanceStatus.PRESENT);
        double percentage = total == 0 ? 0.0 : Math.round((present * 10000.0) / total) / 100.0;
        return Map.of("studentId", student.getId(), "studentName", student.getName(),
            "totalClasses", total, "present", present, "absent", total - present,
            "attendancePercentage", percentage);
    }
}
