# Etapa 1: Build da aplicação
FROM maven:3.8.3-openjdk-17 AS build

# Defina o diretório de trabalho para a etapa de build
WORKDIR /app

# Copie os arquivos de configuração do Maven
COPY pom.xml .

# Baixe as dependências do Maven (essa etapa é separada para aproveitar o cache)
RUN mvn dependency:go-offline

# Copie todo o código-fonte do projeto para a imagem
COPY src ./src

# Execute o build da aplicação
RUN mvn clean package

# Etapa 2: Criação da imagem final
FROM openjdk:17

# Adicione um volume apontando para /tmp
VOLUME /tmp

# Exponha a porta 8080 para o mundo exterior
EXPOSE 8081

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR da etapa de build para a imagem final
COPY --from=build /app/target/loan-service-1.0.0.jar app.jar

# Execute o arquivo JAR
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
