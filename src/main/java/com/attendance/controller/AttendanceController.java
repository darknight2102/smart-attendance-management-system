package com.attendance.controller;

import jakarta.validation.Valid;
import com.attendance.model.Attendance;
import com.attendance.model.AttendanceStatus;
import com.attendance.service.AttendanceService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController @Validated
@RequestMapping("/api")
public class AttendanceController {
    private final AttendanceService service;
    public AttendanceController(AttendanceService service) { this.service = service; }

    public record MarkAttendanceRequest(@NotNull Long studentId,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @NotBlank String subject, @NotNull AttendanceStatus status) {}

    @PostMapping("/attendance") @PreAuthorize("hasRole('ADMIN')")
    public Attendance mark(@Valid @RequestBody MarkAttendanceRequest request) {
        return service.mark(request.studentId(), request.date(), request.subject(), request.status());
    }

    @GetMapping("/attendance") @PreAuthorize("hasRole('ADMIN')")
    public List<Attendance> all() { return service.all(); }

    @GetMapping("/attendance/date/{date}") @PreAuthorize("hasRole('ADMIN')")
    public List<Attendance> byDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.byDate(date);
    }

    @GetMapping("/attendance/student/{studentId}") @PreAuthorize("hasRole('ADMIN')")
    public List<Attendance> byStudent(@PathVariable Long studentId) { return service.byStudent(studentId); }

    @GetMapping("/reports/student/{studentId}") @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> report(@PathVariable Long studentId) { return service.report(studentId); }
}

