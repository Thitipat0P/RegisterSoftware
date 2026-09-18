# RegisterSoftware
```
src/
│
├── main/
│   └── Main.java
│
├── model/
│   ├── User.java
│   ├── Student.java
│   ├── Admin.java
│   │
│   ├── Course.java
│   ├── Section.java
│   ├── TimeSlot.java
│   ├── Registration.java
│   └── RegistrationStatus.java
│
├── exception/
│   ├── CourseFullException.java
│   ├── AlreadyRegisteredException.java
│   └── ScheduleConflictException.java
│
├── repository/
│   ├── CourseRepository.java
│   ├── StudentRepository.java
│   ├── SectionRepository.java
│   ├── RegistrationRepository.java
│   └── CsvRepository.java
│
├── service/
│   ├── AuthenticationService.java
│   ├── CourseService.java
│   └── RegistrationService.java
│
├── ui/
│   ├── LoginGUI.java
│   ├── AdminDashboard.java
│   ├── StudentDashboard.java
│   ├── CreateSectionGUI.java
│   ├── CourseListGUI.java
│   └── MyCoursesGUI.java
│
└── util/
    ├── Session.java
    └── Validation.java
