School Management System
This project is a School Management System designed to manage roles and functionalities for Admin, Teachers, and Students. Below is an overview of the system's workflow and key features.

Roles and Responsibilities
1. Admin
The Admin is responsible for managing user enrollment and system configurations:

Enrollment:
For Students:
Assign a username: Roll Number.
Set the default password: Date of Birth (DOB).
For Teachers:
Assign a username: Phone Number.
Set the default password: Phone Number.
Can delete user entries (students/teachers) if updates to critical fields like the username (e.g., roll number for students) are needed. Deleted users must be re-registered afresh.
2. Student
Students can:

Login:
Use the provided username (Roll Number) and password (DOB).
Update Details:
Edit personal information such as name, email, or address.
Update the password for their account.
Username Changes:
Request an update to their Roll Number. The Admin must delete and re-register the student.
3. Teacher
Teachers can:

Login:
Use the provided username (Phone Number) and password (Phone Number).
Update Details:
Edit personal information such as name, email, or address.
Update the password for their account.
Username Changes:
Request an update to their Phone Number. The Admin must delete and re-register the teacher.
Workflow
Admin Enrollment:

Admin enrolls Students and Teachers, providing usernames and default passwords.
Credentials are communicated to the respective users.
User Login:

Students and Teachers log in using the credentials provided by the Admin.
Details Management:

Students and Teachers can update their additional details and edit existing details (excluding usernames).
Critical Updates:

To update a username (Roll Number for students or Phone Number for teachers), the Admin must delete the user's current entry and re-register them with updated details.
