# EMP_MNG
This project aims to automate a portion of the Employee management. This project covers Employee details provisioning, User and Roles management, Allocating Jobs and assign payments to the employee.

# Dependencies
Java 8 JDK,
NPM,
Maven,
Embedded Tomcat 9 server,
MySQL Database

# Angular CLI Installation
npm install -g @angular/cli

# Swagger Configuration
dependency added in pom.xml
<dependency>
<groupId>org.springdoc</groupId>
<artifactId>springdoc-openapi-ui</artifactId>
<version>1.6.4</version>
</dependency>

url : http://localhost:8081/swagger-ui/index.html
(user service server port added)

*websecurity configuration
@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**","/h2-console/**","/localhost:4200/**");
    }
	
# Create angular project "emp_mng_angular".
ng new emp_mng_angular
	
# Create Service & Components using Angular CLI
Go to ur project directory and run the command on cmd :
- ng g s user
– ng g c home
– ng g c createjob
– ng g c employee-list
- ng g c update-employee

# Create build for angular
- ng build

# To start angular application
- ng serve