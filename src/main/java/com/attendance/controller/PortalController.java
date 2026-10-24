package com.attendance.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/student-portal")
public class PortalController {
    @GetMapping("/welcome")
    @PreAuthorize("hasRole('STUDENT')")
    public Map<String, String> welcome() {
        return Map.of("message", "Student portal access granted",
                      "nextStep", "Link the authenticated account to a student record to show personal attendance.");
    }
}
