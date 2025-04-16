# Etapa de build
FROM maven:3.9.9-amazoncorretto-21 AS build

# Copia o arquivo pom.xml e o código-fonte para o container
COPY pom.xml /app/
COPY src /app/src

# Executa o build do projeto, ignorando os testes
RUN ["mvn", "-f", "/app/pom.xml", "clean", "package", "-Dmaven.test.skip=true"]

# Etapa de execução
FROM amazoncorretto:21

# Define o nome correto do JAR gerado
ARG JAR_NAME="consultas-0.0.1-SNAPSHOT.jar"

# Copia o JAR gerado na etapa de build para o container
COPY --from=build /app/target/${JAR_NAME} /app/${JAR_NAME}

# Expõe a porta usada pela aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/consultas-0.0.1-SNAPSHOT.jar"]