#Online classroom for a school
## Introduction
This is a Spring Boot application which creates a virtual classroom for one school. It has three main roles - admin, teacher and student.
Only the admin can add/delete students and teachers to the database. The other two can register to the website only if they are already on the list.
Users with the 'teacher' role can add/delete assignments, give grades, make statistics about students.
Users with the 'student' role can view/complete their assignments, see their grades, write messages to their teachers.

##Starting the application
To fill the database with info about students and teachers, after you start the application, you should execute the sql script from the resources\sql\initInfo.sql file.