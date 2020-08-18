FROM openjdk:11-jre-slim
EXPOSE 5005
EXPOSE 8090
ARG JAR_FILE
ADD  ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
ENTRYPOINT [ "sh", "-c", "java -Duser.timezone=America/Sao_Paulo $JAVA_OPTS -jar /app.jar" ]
