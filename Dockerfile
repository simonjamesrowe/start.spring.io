FROM openjdk:11-jre-slim
ENV PORT 8080
ENV CLASSPATH /opt/lib
EXPOSE 8080

# copy pom.xml and wildcards to avoid this command failing if there's no target/lib directory
COPY pom.xml target/lib* /opt/lib/

# NOTE we assume there's only 1 jar in the target dir
# but at least this means we don't have to guess the name
# we could do with a better way to know the name - or to always create an app.jar or something
RUN mkdir -p /opt/templates
COPY start-site/src/main/resources/templates /opt/templates


COPY start-site/target/start-site-exec.jar /opt/app.jar
ENV TEMPLATE_ROOT_PATH=/opt
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]