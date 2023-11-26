FROM openjdk:17
COPY "./target/ProyectoRentaCar-1.jar" "app.jar"
EXPOSE 8088
ENTRYPOINT [ "java", "-jar", "app.jar" ]