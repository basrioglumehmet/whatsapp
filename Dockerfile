# JDK Image
FROM openjdk:21-jdk

# Çalışma dizinini oluştur
WORKDIR /app

# Build edilen JAR dosyasını kopyala
COPY target/*.jar app.jar

# Port adresi
EXPOSE 8081

# Giriş noktası
ENTRYPOINT ["java", "-jar", "app.jar"]
