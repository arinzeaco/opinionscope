FROM openjdk:19-oracle
COPY target/*.jar opinion.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","opinion.jar"]