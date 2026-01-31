# Enotes - Personal notes manager

## Technologies: Java, Spring Boot, MVC, Thymeleaf, Spring Data JPA, Git

#### Enotes is a secure full-stack web application designed for managing personal notes. Users can register, log in, and easily create, update, delete, and list their notes. This application leverages the power of Spring Boot, Spring Security for user authentication and authorization, and Spring Data JPA for seamless integration with a MySQL database to persist user data and notes.

![image alt](https://github.com/Pankajword/Enotes-Springboot/blob/b4b882edade7d7dc0ab32f3cdb61131f7d3e12e9/HomeScreenshot.png)


## Key Features:
  User Authentication and Authorization: Implemented using Spring Security, ensuring secure login and registration.

  CRUD Operations on Notes: Users can create, update, delete, and view their notes via a user-friendly Thymeleaf-powered interface.

  Responsive Interface: Designed a simple and intuitive interface for efficient note management, fully responsive across devices.

  Pagination Support: Notes are displayed with pagination, allowing users to navigate through their notes easily without overwhelming the interface. This improves performance and user     experience, especially with large datasets.

  Persistent Storage: Integrated MySQL with Spring Data JPA for secure and reliable data storage.

## Technologies Used :
Backend: Java (version 17) , Spring Boot (version 3.4.5) , Spring Security, Spring Data JPA

Frontend: Thymeleaf

Database: MySQL (version 9)

Version Control: Git

Platform: Intellij IDE

## How to run this program

git clone https://github.com/Pankajword/Enotes-Springboot


###### Set Up the Database

Create a MySQL database named enotes_db:

CREATE DATABASE enotes_db;

#### Update the database configuration in the src/main/resources/application.properties file to match your local MySQL username and password:

spring.datasource.url=jdbc:mysql://localhost:3306/enotes_db

spring.datasource.username=your_mysql_username

spring.datasource.password=your_mysql_password

## ScreenShot

###### Register user
![image alt](https://github.com/Pankajword/Enotes-Springboot/blob/8bad5af1844755ea8222041faf7bbef2edcb9710/RegisterScreenshot.png)

##### Insert Notes
![image alt](https://github.com/Pankajword/Enotes-Springboot/blob/0987953653ef2d240bee3ef2d400cbc9bf2f6553/ProjectScreenshot.png)

##### View Notes

![image alt](https://github.com/Pankajword/Enotes-Springboot/blob/0f3e150a0dbdbc16a0c8f24e4968e35f257ad05a/viewscreenshot.png)







