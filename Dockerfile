FROM nginx:1.10-alpine
ADD target/gf.jar /
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /gf.jar" ]
