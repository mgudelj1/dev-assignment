# Developer Setup
- JDK 1.8


# Running the Project

The project uses an embedded Tomcat server.

1. Run 'run.sh' placed in root from command line with ./run.sh
2. Wait for the application to build and start
3. Open http://localhost:8080/ to view the application
4. Visit http://localhost:8080/swagger-api.html to see the API documentation
5. Check out test/resources/edpointTests.postman_collection.json for quick use with Postman

Considerations:

Considering todays best practices and most widely usage and considering I have most experience with,
I would recommend implementing OAuth token based authentication. These tokens provide us not only
with authentication, but also possibility to use it  with authorization which means they could be used in
limited the scope of API and also they provide age validity.

Redundancy is accomplished by having multiple services deployed on multiple servers. 
Considering SLA, we could have different kind of setups. Basically we would need
to have at least 2 different physical servers(if possible different building, city, different tectonic plate?)
with multiple application servers installed on them and load balancer between them.