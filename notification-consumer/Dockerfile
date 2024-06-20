FROM openjdk:17

WORKDIR /app

COPY build/libs/notification-consumer-0.0.1-SNAPSHOT.jar .

EXPOSE ${CART_PORT}

CMD ["java", "-jar", "notification-consumer-0.0.1-SNAPSHOT.jar"]