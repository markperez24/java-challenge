### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions
- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java
- I have 10+ years of experience in Java, and I started to use Spring Boot from 2019
- Mostly I have been involved in Java and JavaScript projects
- Although under 5 years of usage experience, I know Spring Boot enough to implement it.
- I have 5 years straight of developing Web Applications incorporating Java, JavaScript, jQuery Mobile, etc. under an Airlines Domain (under Delta Airlines)
- I have more than a year of experience migrating old VB codes to Java EE for a Japanese Financial client (2012~2013, location: Tokyo) 
- I just began doing minimal coding involvement in Android development starting this year 2023.


#### Summary of Code Additions & Changes
- Made some modifications on the API Mapping URL for better readability
- Implemented a simple yet proper exception handling in the Controller class to handle exceptions
- Updated each API method's return type to ResponseEntity 
- Added validators, annotations, EmployeeDTO, custom exception handlers
- Documentation (Applied Javadoc comments)
- Employed logging instead of System.out.println
- Added a constructor in the Employee entity
- Utilized Spring's Simple Caching Logic
- Created a new Security Configuration Class to handle the Controller End Points security using Spring Security, and In-Memory User Configurations
- Created the Unit Test class for EmployeeController
- Used Mockito to implement UT
- Added necessary dependencies in pom.xml, namely for Spring Security, and Mockito
- Modified application.properties to contain user config details & roles, and caching definition


#### Code or Process Improvement
These are some of the things that can improve the demo application.
- I don't have enough test cases for Controller and for other classes
- Create UI depicting the API functionalities from the client standpoint (e.g. Angular)
- Consider utilizing DTOs to separate the API contracts
- Add more specific error handling and responses for each endpoint, using the original HTTP Status codes from the API design 
- For the caching logic, probably try to change @CacheEvict with @CachePut to update cache for the individual operations
- achieve 100% test coverage
- Try other caching providers like EHCache