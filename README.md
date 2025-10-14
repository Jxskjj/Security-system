# SecurityEx - Spring Boot Security Application

## 📋 Описание проекта

SecurityEx - это веб-приложение на Spring Boot, демонстрирующее реализацию системы аутентификации и авторизации с использованием JWT токенов. Проект включает в себя ролевую модель доступа (USER/ADMIN) и интеграцию с PostgreSQL базой данных.

## 🛠 Технологический стек

### Backend
- **Java 24** - основной язык программирования
- **Spring Boot 3.5.3** - фреймворк для создания веб-приложений
- **Spring Security** - система безопасности и аутентификации
- **Spring Data JPA** - работа с базой данных через ORM
- **JWT (JSON Web Tokens)** - для stateless аутентификации
  - `jjwt-api` (0.12.6)
  - `jjwt-impl` (0.12.6) 
  - `jjwt-jackson` (0.12.6)

### База данных
- **PostgreSQL** - реляционная база данных
- **Hibernate** - ORM для работы с БД

### Дополнительные библиотеки
- **Lombok** - упрощение написания boilerplate кода
- **ModelMapper** - маппинг между DTO и Entity объектами
- **Spring Boot Validation** - валидация входных данных
- **Spring Boot DevTools** - инструменты для разработки

### Инструменты сборки
- **Maven** - система сборки и управления зависимостями
- **Spring Boot Maven Plugin** - плагин для запуска приложения

## 🗄 Настройка базы данных

### 1. Установка PostgreSQL

#### Windows:
1. Скачайте PostgreSQL с официального сайта: https://www.postgresql.org/download/windows/
2. Запустите установщик и следуйте инструкциям
3. Запомните пароль для пользователя `postgres`

#### macOS:
```bash
# Установка через Homebrew
brew install postgresql
brew services start postgresql
```

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

### 2. Создание базы данных

1. Откройте командную строку/терминал
2. Подключитесь к PostgreSQL:
```bash
psql -U postgres
```

3. Создайте базу данных:
```sql
CREATE DATABASE security;
```

4. Выйдите из psql:
```sql
\q
```

### 3. Настройка подключения

Отредактируйте файл `src/main/resources/application.properties`:

```properties
# Настройки базы данных
spring.datasource.url=jdbc:postgresql://localhost:5432/security
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ_ОТ_POSTGRES

# Настройки JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**⚠️ Важно:** Замените `ВАШ_ПАРОЛЬ_ОТ_POSTGRES` на ваш реальный пароль от PostgreSQL.

## 🚀 Запуск проекта

### Предварительные требования

- **Java 24** или выше
- **Maven 3.6+**
- **PostgreSQL** (установленная и настроенная)

### Проверка установки Java и Maven

```bash
# Проверка версии Java
java -version

# Проверка версии Maven
mvn -version
```

### Способы запуска

#### 1. Через Maven (рекомендуемый)

```bash
# Клонирование репозитория
git clone <URL_ВАШЕГО_РЕПОЗИТОРИЯ>
cd security

# Сборка проекта
mvn clean install

# Запуск приложения
mvn spring-boot:run
```

#### 2. Через IDE

1. Откройте проект в IntelliJ IDEA или Eclipse
2. Дождитесь загрузки зависимостей Maven
3. Найдите класс `SecurityExApplication.java`
4. Запустите метод `main()`

#### 3. Через JAR файл

```bash
# Сборка JAR файла
mvn clean package

# Запуск JAR файла
java -jar target/securityEx-0.0.1-SNAPSHOT.jar
```

### Проверка запуска

После успешного запуска приложение будет доступно по адресу:
- **URL:** http://localhost:8080
- **Главная страница:** http://localhost:8080/

Вы должны увидеть сообщение: `"welcome to our site. Session id: [ID_СЕССИИ]"`

## 📚 API Endpoints

### Публичные endpoints (без аутентификации)
- `GET /` - главная страница
- `POST /register` - регистрация нового пользователя
- `POST /login` - аутентификация пользователя

### Защищенные endpoints

#### Для пользователей с ролью USER и ADMIN:
- `GET /user/**` - пользовательские ресурсы

#### Только для администраторов:
- `GET /admin/**` - административные ресурсы

## 🔐 Система безопасности

### Роли пользователей
- **USER** - обычный пользователь
- **ADMIN** - администратор системы

### Аутентификация
- Используется JWT (JSON Web Token) для stateless аутентификации
- Пароли хешируются с помощью BCrypt (strength = 12)
- Сессии не сохраняются (STATELESS)

### Авторизация
- Контроль доступа на основе ролей
- JWT фильтр проверяет токены на каждом запросе

## 📁 Структура проекта

```
src/
├── main/
│   ├── java/com/hdifoe/securityex/
│   │   ├── configuration/          # Конфигурация безопасности
│   │   │   ├── filters/           # JWT фильтр
│   │   │   └── SecurityConfiguration.java
│   │   ├── controllers/           # REST контроллеры
│   │   │   ├── AdminController.java
│   │   │   ├── HomeController.java
│   │   │   └── UserController.java
│   │   ├── dto/                   # Data Transfer Objects
│   │   │   └── UserDto.java
│   │   ├── model/                 # Модели данных
│   │   │   ├── Role.java
│   │   │   ├── User.java
│   │   │   └── UserPrincipial.java
│   │   ├── repository/            # Репозитории для работы с БД
│   │   │   └── UserRepository.java
│   │   ├── service/               # Бизнес-логика
│   │   │   ├── JWTService.java
│   │   │   ├── PersonalUserDetailService.java
│   │   │   └── UserService.java
│   │   ├── utils/                 # Утилиты и исключения
│   │   │   ├── UserErrorResponse.java
│   │   │   ├── UserNotCreatedException.java
│   │   │   └── UserValidator.java
│   │   └── SecurityExApplication.java  # Главный класс
│   └── resources/
│       ├── application.properties # Конфигурация приложения
│       ├── static/               # Статические ресурсы
│       └── templates/            # HTML шаблоны
└── test/                         # Тесты
```

## 🧪 Тестирование

### Запуск тестов
```bash
mvn test
```

### Запуск конкретного теста
```bash
mvn test -Dtest=SecurityExApplicationTests
```

## 🔧 Конфигурация для разработки

### Настройки для разработки (application-dev.properties)
Создайте файл `src/main/resources/application-dev.properties`:

```properties
# Настройки для разработки
spring.datasource.url=jdbc:postgresql://localhost:5432/security_dev
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ

# Логирование
logging.level.com.hdifoe.securityex=DEBUG
logging.level.org.springframework.security=DEBUG

# Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Запуск с профилем разработки
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🐛 Решение проблем

### Проблема: "Connection refused" при подключении к БД
**Решение:**
1. Убедитесь, что PostgreSQL запущен
2. Проверьте правильность настроек в `application.properties`
3. Убедитесь, что база данных `security` создана

### Проблема: "Port 8080 already in use"
**Решение:**
1. Остановите другое приложение на порту 8080
2. Или измените порт в `application.properties`:
```properties
server.port=8081
```

### Проблема: "Class not found" или ошибки компиляции
**Решение:**
```bash
mvn clean install
mvn dependency:resolve
```

## 📝 Дополнительные настройки

### Изменение порта приложения
Добавьте в `application.properties`:
```properties
server.port=8081
```

### Настройка логирования
Добавьте в `application.properties`:
```properties
logging.level.root=INFO
logging.level.com.hdifoe.securityex=DEBUG
```
