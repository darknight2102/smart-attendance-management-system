package com.attendance.repository;

import com.attendance.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentIdOrderByAttendanceDateDesc(Long studentId);
    List<Attendance> findByAttendanceDate(LocalDate date);
    boolean existsByStudentIdAndAttendanceDateAndSubject(Long studentId, LocalDate date, String subject);
    long countByStudentId(Long studentId);
    long countByStudentIdAndStatus(Long studentId, com.attendance.model.AttendanceStatus status);
}
