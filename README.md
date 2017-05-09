## Prerequisites

The project expects Java 8 and gradle to be installed.


## Usage

### Running the Service

./gradlew bootRun # use 'gradlew bootRun' on Windows

Distributing the Service ./gradlew build # use 'gradlew build' on Windows

generates the binary ./build/libs/XXXXX.jar

A Dockerfile is available to build an image. The image build process can be launched using

./gradlew buildDocker # use 'gradlew buildDocker' on windows`

## Decision made
###Geolocation
To obtain the geolocation for the shops have been resolve using the GMaps Api.

###Persistence
The persistence have been done by H2 in memory database, its light, fast, and easy to use.
With H2, your database is created by Hibernate every time you start the application. 
Thus, the database is brought up in a known and consistent state. It also allows you to develop and test your JPA mappings.

H2 ships with a web based database console, which you can use while your application is under development.
It is a convenient way to view the tables created by Hibernate and run queries against the in memory database. 

the URL to access the H2 console is: http://localhost:8080/console

#### Rest

The service is based on Spring HATEOAS, giving the URL to the shops.
The service validates the request's body and tries to return meaningful errors.

## Testing the Service

Integration test are done in a real test environment created by Spring Boot at runtime.

###Java

Some Java integration tests are included. To execute them:

gradlew.bat test on windows

###Postman

A `shopDemoChallenge.postman_collection` have been added to the project to make it easy to test it. 
To run it, use the _Postman_ GUI and then execute the test suite
