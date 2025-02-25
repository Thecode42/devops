FROM tomcat:9.0-jdk17-openjdk-slim

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/artefacto.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]