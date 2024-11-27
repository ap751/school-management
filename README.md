​**School Management System**

The School Management System is designed to efficiently oversee the roles and functionalities of Administrators, Teachers, and Students. Below, you will find an overview of the system's workflow and its key features.

**Roles and Responsibilities**

1. **Administrator**  
The Administrator is tasked with managing user enrollment and configuring system settings:

   - **Enrollment:**
     - **For Students:**  
       - Username: Assigned as the Roll Number.  
       - Default Password: Set to the Date of Birth (DOB).  
     - **For Teachers:**  
       - Username: Assigned as the Phone Number.  
       - Default Password: Set to the Phone Number.  
     - The Administrator has the authority to delete user entries (for both students and teachers) when updates to critical fields, such as the username (e.g., Roll Number for students), are necessary. Deleted users must be re-registered as new entries.

2. **Student**  
Students have the following capabilities:

   - **Login:**  
     - Access the system using the provided username (Roll Number) and password (DOB).  
   - **Update Details:**  
     - Modify personal information such as name, email, or address.  
     - Change the password for their account.  
   - **Username Changes:**  
     - Request an update to their Roll Number. The Administrator must delete and re-register the student to effect this change.

3. **Teacher**  
Teachers are empowered to:

   - **Login:**  
     - Access the system using the provided username (Phone Number) and password (Phone Number).  
   - **Update Details:**  
     - Modify personal information such as name, email, or address.  
     - Change the password for their account.  
   - **Username Changes:**  
     - Request an update to their Phone Number. The Administrator will need to delete and re-register the teacher for this change to take place.

**Workflow**

- **Admin Enrollment:**  
  The Administrator enrolls Students and Teachers, assigning usernames and default passwords, which are then communicated to the respective users.

- **User Login:**  
  Students and Teachers log in using the credentials provided by the Administrator.

- **Details Management:**  
  Students and Teachers can update additional details and modify existing information (excluding usernames).

- **Critical Updates:**  
  To change a username (Roll Number for students or Phone Number for teachers), the Administrator must delete the user’s current entry and re-register them with the updated details.
