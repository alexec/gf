FROM openjdk:8-jre-alpine
ADD target/games.jar /
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /games.jar" ]
