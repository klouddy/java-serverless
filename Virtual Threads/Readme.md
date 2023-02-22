# Using Virtual Threads

Virtual threads are an exciting new experimental feature in Java 19.  There is plenty of hype building about this: https://www.infoq.com/articles/java-virtual-threads/

Virtual threads are a potential benefit in the land of Java and Serverless because it has the potential to reduce Memmory consumption
for high throughput loads.  Especially when you have a blocking call you are handling.  For instance if you have a rest request that depends on a long running 
database query.

There are 2 examples projects here.  

Both projects call an external api and that api takes 3 seconds to respond.  This setup will help to mimic a situation you might have in real life.

The project `standard-tomcat` is a traditional Spring MVC app.   The project `virtual-threads-tomcat` is a Spring MVC project that uses virtual threads.

## Runing the examples


There are few tools you might want to install

- [SDKMAN](https://sdkman.io/)
- nodejs 19
- [k6](https://k6.io/)


Start the mock server

```
cd mock-server
npm install
npm run start
```

Start virtual thread server

```
cd VirtualThreadsTomcat
sdk use java 19.0.2-tem
mvn spring-boot:run
```

Start Standard Server

```
cd StandardMVCTomcat
sdk use java 17.0.6-tem
mvn spring-boot:run
```

run the load script

```
cd k6
k6 