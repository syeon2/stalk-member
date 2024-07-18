FROM openjdk:17.0.2-slim

COPY ./bootstrap/build/libs/*.jar ./stalkMember.jar

ARG ENCRYPT_PASSWORD

ENV ENCRYPT_PASSWORD=$ENCRYPT_PASSWORD

ENTRYPOINT ["java", "-jar", "./stalkMember.jar"]
