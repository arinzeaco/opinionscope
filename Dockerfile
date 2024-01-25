FROM openjdk:19-oracle
COPY target/*.jar opinion.jar
#EXPOSE 8089
CMD ["java","-jar","opinion.jar"]