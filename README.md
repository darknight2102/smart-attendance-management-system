# Smart Attendance Management System

A starter project using Java, Spring Boot, Spring Web, Spring Data JPA, MySQL, Spring Security, and a simple HTML/CSS/JavaScript dashboard.

## Features included
- Student CRUD REST APIs
- Mark attendance as PRESENT or ABSENT
- Filter attendance by date and student
- Attendance percentage report
- HTTP Basic authentication with ADMIN and STUDENT roles
- BCrypt password encoding for the demo users
- Database-level unique constraint to prevent duplicate attendance for the same student/date/subject
- Browser dashboard at `/`

## Requirements
- JDK 17 or newer (JDK 21 is fine)
- Maven 3.6+
- MySQL 8+
- Internet connection on first Maven build to download dependencies

## Run locally
1. Create a database:
   ```sql
   CREATE DATABASE smart_attendance;
   ```
2. Set your database environment variables in PowerShell (use your actual MySQL password):
   ```powershell
   $env:DB_URL="jdbc:mysql://localhost:3306/smart_attendance"
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="YOUR_MYSQL_PASSWORD"
   ```
3. From the project folder, run:
   ```powershell
   mvn spring-boot:run
   ```
4. Open `http://localhost:8080/`.

## Demo credentials
- Admin: `admin` / `Admin123!`
- Student: `student` / `Student123!`

These demo accounts are stored in memory and reset on restart. Do not use demo credentials or HTTP Basic over plain HTTP in production. For a production project, store users in MySQL, use HTTPS, and consider JWT or session-based authentication.

## API endpoints
All `/api/**` endpoints require authentication.

| Method | Endpoint | Role | Purpose |
|---|---|---|---|
| GET | `/api/students` | ADMIN, STUDENT | List students |
| GET | `/api/students/{id}` | ADMIN, STUDENT | Get one student |
| POST | `/api/students` | ADMIN | Add a student |
| PUT | `/api/students/{id}` | ADMIN | Update student |
| DELETE | `/api/students/{id}` | ADMIN | Delete student |
| POST | `/api/attendance` | ADMIN | Mark attendance |
| GET | `/api/attendance` | ADMIN | List all attendance |
| GET | `/api/attendance/date/{yyyy-mm-dd}` | ADMIN | Filter by date |
| GET | `/api/attendance/student/{id}` | ADMIN | Student attendance history |
| GET | `/api/reports/student/{id}` | ADMIN | Attendance summary and percentage |
| GET | `/api/student-portal/welcome` | STUDENT | Student-role access test |

## Example requests
Create a student (Basic Auth admin):
```json
POST /api/students
{
  "name": "Aarav Sharma",
  "rollNumber": "CS101",
  "email": "aarav@example.com",
  "course": "B.Tech CSE"
}
```

Mark attendance:
```json
POST /api/attendance
{
  "studentId": 1,
  "date": "2026-10-24",
  "subject": "Computer Networks",
  "status": "PRESENT"
}
```

Example report response:
```json
{
  "studentId": 1,
  "studentName": "Aarav Sharma",
  "totalClasses": 10,
  "present": 8,
  "absent": 2,
  "attendancePercentage": 80.0
}
```
The report numbers above are illustrative. The actual API computes values from your database.

## Important limitations to understand
- Authentication uses Spring Security HTTP Basic and in-memory demo users; it is not JWT and user accounts are not persisted in MySQL.
- The student portal is a role-access demonstration. It is not yet linked to an individual student's own records.
- The HTML dashboard is a lightweight demo, not a production-ready frontend.
- `ddl-auto=update` is for local development. Use migrations and stronger operational settings in production.
- Test all endpoints in your environment before presenting the project as working.
