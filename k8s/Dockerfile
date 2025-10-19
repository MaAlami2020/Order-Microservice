##BACKEND

#addition of java image
FROM maven:3.9.6-eclipse-temurin-21 AS backend

#definition of the work directory in /project to execute commands
WORKDIR /project

#project dependencies are copied on the work directory
COPY pom.xml /project/

#project dependencies are downloaded
RUN mvn clean verify --fail-never

#backend project code is copied on the work directory
COPY backend/src /project/src

# files are copied
# origin --> dist/frontend
# destiny --> src/main/resources/static/(new)
#COPY --from=builder /project/dist/frontend /project/src/main/resources/static

# app building once the code is compiled
RUN mvn clean package -DskipTests=true

## image for the app container
FROM eclipse-temurin:21-jdk

# definition of the work directory where JAR file finds
WORKDIR /usr/src/app/

RUN curl -LJO https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
&& chmod +x /usr/src/app/wait-for-it.sh

# JAR file of compilation container is copied on JAR work directory
COPY --from=backend /project/target/*.jar /usr/src/app/
COPY --from=backend /project/target/webapp1a-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar

# Copiar wait-for-it
#COPY wait-for-it.sh .

# Dar permisos al script
#RUN chmod +x wait-for-it.sh

EXPOSE 8445

# Espera a MySQL y Redis antes de arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]