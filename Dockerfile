FROM maven:3.8.2-jdk-8
MAINTAINER Raul França
WORKDIR /api
COPY . /src
RUN mvn clean install

CMD mvn spring-boot:run
#CMD java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost:3306/vollmed_api -DDATASOURCE_USERNAME=root -DDATASOURCE_PASSWORD=root -jar api.jar
