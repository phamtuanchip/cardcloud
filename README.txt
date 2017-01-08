# Spring 4 MVC Example (Java Configuration) + Maven
Template example for Spring 4 MVC + JSP View with pure Java Configuration (no XML), using Maven build tool.

###1. Technologies
* Spring 4.3.0.RELEASE
* Maven 3.3.3
* JSTL 1.2

###2. To Run this project locally
```shell
$ git clone https://github.com/viralpatel/spring4-mvc-example
$ mvn tomcat7:run
```
Access ```http://localhost:8080/spring4/hello```

###3. To import this project in Eclipse IDE
1. ```$ mvn eclipse:eclipse```
2. Import into Eclipse via **existing projects into workspace** option.
3. Done. 


###3. Project Demo
Please refer to this article [Spring 4 MVC Hello World](http://viralpatel.net/blogs/spring-4-mvc-tutorial-maven-example/)

# Spring 4 RESTFul Controller Example (REST CRUD Example)
Template example for Spring 4 MVC + RESTful Service with pure Java Configuration (no XML), using Maven build tool.

###1. Technologies
* Spring 4.3.1.RELEASE
* Maven 3.3.3

###2. To Run this project locally
```shell
$ git clone https://github.com/viralpatel/spring4-restful-example
$ mvn tomcat7:run
```
Access ```http://localhost:8080/spring4/customers```

![Spring 4 REST Tutorial](http://img.viralpatel.net/2016/06/spring-4-mvc-rest-controller-service-restful.png) 

###3. To import this project in Eclipse IDE
1. ```$ mvn eclipse:eclipse```
2. Import into Eclipse via **existing projects into workspace** option.
3. Done. 


###4. Project Demo
Please refer to this article [Spring 4 RESTFul Service Tutorial](http://viralpatel.net/blogs/spring-4-mvc-rest-example-json/)

#### encode
mvn -Dmaven.tomcat.uriEncoding=UTF-8 tomcat7:run