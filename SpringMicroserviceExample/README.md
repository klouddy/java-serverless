# Simple Spring Boot App For Testing

## CDS

Class Data Sharing.  Can speed up spring startup.

To create a file:

```
# Buiild jar file
./gradlew build

# Create .jsa file
# after spring boot starts up go ahead and hit Ctrl + C to shut it down.
java -XX:ArchiveClassesAtExit=app-cds.jsa -jar build/libs/SpringMicroserviceExample-0.0.1-SNAPSHOT.jar

# Run with .jsa file
java -XX:SharedArchiveFile=app-cds.jsa -jar build/libs/SpringMicroserviceExample-0.0.1-SNAPSHOT.jar

```