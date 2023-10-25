# Cloud Web Application
This Project is built using Springboot, Java, Postgresql, Docker.


## Requirements
For building and running the application you need:
 - JDK 19
 - maven 3.8.6
 - Postgresql
 - BCrypt 0.3
 - JUnit 4
 - Mockito 

## Steps to Build, Run & Test 
 
  - Clone the repository it using ssh.
  - Run the below two queries to set up database. 
  ``` 
  CREATE DATABASE app;
  USE app;
   ```
  - mvn clean install && mvn spring-boot:run
  -  PORT :  http://localhost:8080
  - For Testing -   mvn -Dtest=CloudAppApplicationTests test

  - or by using any IDE, you can build & run the project in any IDE.

## Github Actions
Added branch protection by preventing merge if any workflow fails.
Added unit test cases to the workflow to make sure.
AMI is bulit when push is done.

## API Endpoints Curls

### Healthz
```
curl --location --request GET 'http://localhost:8080/healthz' \
--header 'Cookie: JSESSIONID=60495B2988F7210B7555D0445DC94525'
```
