# RegisterSoftware
```
FLOW_CHART
src/
├── main/
│   └── Main.java
│
├── model/
│   ├── User.java
│   ├── Student.java
│   ├── Instructor.java
│   ├── Admin.java
│   │
│   ├── Course.java
│   ├── Section.java
│   ├── TimeSlot.java
│   ├── Registration.java        ⭐ เพิ่ม
│   └── RegistrationStatus.java  ⭐ เพิ่ม
│
├── exception/
│   ├── CourseFullException.java
│   ├── AlreadyRegisteredException.java
│   ├── ScheduleConflictException.java  ⭐ เพิ่ม
│   ├── PrerequisiteException.java      ⭐ เพิ่ม
│   └── AuthenticationException.java   ⭐ เพิ่ม
│
├── repository/
│   ├── Repository.java                ⭐ เพิ่ม Interface
│   ├── StudentRepository.java
│   ├── CourseRepository.java
│   ├── SectionRepository.java
│   └── GoogleSheetRepository.java
│
├── service/
│   ├── AuthenticationService.java
│   ├── RegistrationService.java
│   ├── CourseManageService.java
│   └── ScheduleService.java           ⭐ เพิ่ม
│
├── ui/
│   ├── LoginGUI.java
│   ├── StudentDashboard.java
│   ├── InstructorDashboard.java
│   ├── AdminDashboard.java
│   ├── CourseSearchGUI.java           ⭐ เพิ่ม
│   ├── CourseDetailGUI.java           ⭐ เพิ่ม
│   └── ScheduleGUI.java               ⭐ เพิ่ม
│
└── util/                              ⭐ เพิ่ม
    ├── Session.java
    └── Validation.java
