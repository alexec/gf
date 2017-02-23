FROM openjdk:8-jre-alpine
ADD target/gf.jar /
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /gf.jar" ]
