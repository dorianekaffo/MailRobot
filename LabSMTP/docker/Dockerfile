FROM openjdk:8-jre-alpine
# copy JAR into image
COPY MockMock-1.4.0.one-jar.jar /MockMock-1.4.0.one-jar.jar
# run application with this command line
CMD ["/usr/bin/java", "-jar", "/MockMock-1.4.0.one-jar.jar", "-p", "2525", "-h", "8080"]
