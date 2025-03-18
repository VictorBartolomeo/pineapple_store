FROM ubuntu:latest
LABEL authors="victor"

#installe les dépendances
RUN apt-get update
RUN apt-get install -y openjdk-21-jdk-headless #-y sert à tout valider de facto

#Créer une dossier opt app
RUN mkdir /opt/app

#Copie le fichier de notre machine locale vers /opt/app/myapp.jar
COPY target/premier_Projet_Spring-0.0.1-SNAPSHOT.jar /opt/app/myapp.jar

#Signale à docker quel est le dossier de travail
WORKDIR /opt/app

#Démarre l"application
ENTRYPOINT ["java", "-jar" , "myapp.jar"]