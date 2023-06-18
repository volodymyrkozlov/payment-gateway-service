FROM openjdk:17

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,address=10201,server=y,suspend=n
ADD payment-gateway-service/target/payment-gateway-service.jar /app/payment-gateway-service.jar

ENTRYPOINT ["java", "-jar", "/app/payment-gateway-service.jar"]