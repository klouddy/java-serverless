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

## Custom and slim JRE

To make a smaller JRE that only includes what you need you can do the following.

```
# build jar
./gradlew build

# uncompres to view libraries
jar -xvf build/libs/SpringMicroserviceExample-0.0.1-SNAPSHOT.jar

# run jdeps to get the required modules
jdeps --ignore-missing-deps --multi-release 17 --print-module-deps --class-path BOOT-INF/lib/* build/libs/SpringMicroserviceExample-0.0.1-SNAPSHOT.jar > jre-deps.info

# run jlink to create the jre
jlink --verbose --compress 2 --strip-java-debug-attributes --no-header-files --no-man-pages --output jre-slim --add-modules $(cat jre-deps.info)

# use your new jre-slim to run the app
jre-slim/bin/java -jar build/libs/SpringMicroserviceExample-0.0.1-SNAPSHOT.jar

# curl the endpoint
curl --location --request GET 'http://localhost:8080/people?num=20'
```