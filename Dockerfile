# Этап 1: Сборка приложения
# Используем образ с JDK 21 и Maven для сборки
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Копируем только pom.xml, чтобы закешировать зависимости
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходный код и собираем JAR-файл
COPY src ./src
RUN mvn clean package -DskipTests

# Этап 2: Запуск приложения
# Используем лёгкий образ только с JRE для запуска
FROM eclipse-temurin:21-jre-alpine

# Создаём непривилегированного пользователя для безопасности
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

WORKDIR /app

# Копируем собранный JAR-файл из первого этапа
COPY --from=builder /app/target/*.jar app.jar

# Открываем порт, который будет слушать приложение
EXPOSE 8080

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]