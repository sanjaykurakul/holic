# NexJob (Udyogam) - Job Portal Application

NexJob (Udyogam) is a comprehensive, full-stack Job Portal built using Spring Boot and Thymeleaf. It provides a platform where **Employers** can post and manage job listings, and **Students/Candidates** can browse jobs, upload their resumes, and apply for positions.

## 🚀 Features

### For Employers
- **Employer Dashboard**: View analytics on posted jobs, total applicants, and shortlisted candidates.
- **Job Management**: Post new jobs, edit existing job details, and delete postings.
- **Applicant Tracking**: View a list of candidates who applied for your jobs, download their uploaded resumes, and change their status to "Shortlisted" or "Rejected".

### For Students
- **Student Dashboard**: Track the number of applications, shortlisted jobs, and rejected applications.
- **Resume Management**: Securely upload and manage resumes (stored locally).
- **Job Browsing & Applying**: Search and apply for available jobs posted by employers.
- **Notifications**: Receive in-app notifications whenever an employer shortlists your application.

## 🛠️ Technology Stack

- **Backend**: Java 21, Spring Boot 3.2.x, Spring Web MVC, Spring Security
- **Database**: MySQL, Spring Data JPA, Hibernate
- **Frontend**: HTML5, Vanilla CSS (Glassmorphism UI), Thymeleaf templates, JavaScript
- **Build Tool**: Maven

## ⚙️ Setup & Installation

### Prerequisites
- **Java 21** or higher
- **MySQL** installed and running
- **Maven** (optional, you can use the wrapper)

### Configuration
1. Open the project in your favorite IDE (IntelliJ IDEA recommended).
2. Configure your MySQL database settings in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/udyogam_db
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Make sure to create the database in MySQL:
   ```sql
   CREATE DATABASE udyogam_db;
   ```

### Running the Application
1. Run the `UdyogamApplication.java` main class from your IDE, or run the following Maven command in the project root:
   ```bash
   ./mvnw spring-boot:run
   ```
2. The application will be accessible at `http://localhost:8083`.

### Resume Uploads Note
Uploaded resumes are stored locally in an `uploads/` directory located in the root of the project. Ensure the application has the necessary read/write permissions for this directory.

## 🧑‍💻 Roles & Access

When registering a new account, you can select your role:
- **STUDENT**: Access to the student dashboard, job browsing, and resume uploads.
- **EMPLOYER**: Access to the employer dashboard, job posting, and applicant tracking.

## 🎨 UI/UX Highlights
The user interface features a modern "glassmorphism" aesthetic with a responsive dark-mode layout, complete with custom loading animations and dynamic feedback (sweet alerts and inline notifications).
