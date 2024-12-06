# 1. stage: build
#FROM eclipse-temurin:17.0.11_9-jdk
# eclipse-temurin:17.0.11_9-jre-alpine
FROM maven:3.9.7-eclipse-temurin-17-alpine as builder

WORKDIR /opt/fraud-detection

COPY settings.xml ./
# First, copy the Maven pom.xml file to build without the source code so that modifications to the source code do not invalidate
# the Maven repository cache inside the container, thereby reducing build time.
COPY pom.xml ./

# Build without the source code: Download dependencies
# See https://issues.apache.org/jira/browse/MDEP-516
RUN mvn de.qaware.maven:go-offline-maven-plugin:resolve-dependencies -e -s settings.xml
# mvn -B -s settings.xml dependency:resolve

# Copy the source code and perform the actual build.
COPY src /opt/fraud-detection/src
RUN mvn package -B -e -s settings.xml

# 2. stage: runtime ------------------------------------------------------------
# FROM gcr.io/distroless/java@sha256:40671acefa51d12e33f547fc4950b6de430c905e61ca821d9c16ab5133ede762
# FROM gcr.io/distroless/java:11-nonroot
FROM eclipse-temurin:17.0.11_9-jdk


WORKDIR /opt/fraud-detection
EXPOSE 8080

COPY --from=builder /opt/fraud-detection/target/fraud-detection-app-*-SNAPSHOT.jar /opt/fraud-detection/fraud-detection-app.jar

ENV SPRING_PROFILES_ACTIVE prod
#ENV CONFIG_LOCATION file:/opt/fraud-detection/config/

ENTRYPOINT []

# CMD [\
#     "java", \
#     "-Djava.compiler=NONE -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=0.0.0.0:8888 -Xnoagent", \
#     "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
#     "-Djava.security.egd=file:/dev/./urandom", \
#     "-jar", "/opt/fraud-detection/fraud-detection-app.jar", \
#     "--spring.config.additional-location=${CONFIG_LOCATION}", \
#     "--logging.config=${CONFIG_LOCATION}/logback.xml" \
# ]

CMD [\
    "java", \
    "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "/opt/fraud-detection/fraud-detection-app.jar" \
]
