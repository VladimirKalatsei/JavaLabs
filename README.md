
## 🌟 Языковой Детектор API

<div align="center">

✨ **Лабораторная работа № 5 по Java** ✨  
🚀 *Spring Boot • PostgreSQL • Swagger* 🚀

[![Java](https://img.shields.io/badge/Java-17-FF8C00?logo=java&logoColor=white)](https://java.com)
[![Spring](https://img.shields.io/badge/Spring_Boot-3.1.5-6DB33F?logo=spring&logoColor=white)](https://spring.io)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-4169E1?logo=postgresql&logoColor=white)](https://postgresql.org)
[![Swagger](https://img.shields.io/badge/Swagger-3.0-85EA2D?logo=swagger&logoColor=black)](https://swagger.io)
[![Coverage](https://img.shields.io/badge/Coverage-87%25-brightgreen)](https://github.com)

</div>

<div align="center">
  <img src="https://media.giphy.com/media/juua9i2c2fA0AIp2iq/giphy.gif" width="400" alt="Анимированный детектив">
</div>

## 🌈 Содержание

<div align="center">

| Лабораторная работа | Тема | Иконка |
|---------------------|------|--------|
| [ЛР1](#лабораторная-работа-1) | Basic REST service | <img src="https://cdn-icons-png.flaticon.com/512/2165/2165004.png" width=16> |
| [ЛР2](#лабораторная-работа-2) | JPA (Hibernate/Spring Data) | <img src="https://cdn-icons-png.flaticon.com/512/2772/2772128.png" width=16> |
| [ЛР3](#лабораторная-работа-3) | Data caching | <img src="https://cdn-icons-png.flaticon.com/512/1920/1920810.png" width=16> |
| [ЛР4](#лабораторная-работа-4) | Error logging/handling | <img src="https://cdn-icons-png.flaticon.com/512/463/463612.png" width=16> |
| [ЛР5](#лабораторная-работа-5) | Batch data processing & Testing | <img src="https://cdn-icons-png.flaticon.com/512/2883/2883831.png" width=16> |

</div>

## Технологический стек

<div align="center" style="font-size: 0.9em;">

![Java](https://img.shields.io/badge/Java-17-%23ED8B00?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.5-%236DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-%23336791?logo=postgresql)
![Swagger](https://img.shields.io/badge/Swagger-3.0-%2385EA2D?logo=swagger)
![JUnit5](https://img.shields.io/badge/JUnit-5-%2325A162?logo=junit5)

</div>

## 🏆 Лабораторная работа 1
**Тема:** Базовый REST сервис  
**Особенности:**
- Простейший GET endpoint
- Ответ в формате JSON
- Конфигурация через Maven
- Запуск на Spring Boot 3.1.5


## 🗃️ Лабораторная работа 2
**Тема:** Работа с БД  
**Особенности:**
- Подключение PostgreSQL 15+
- Сущности User и DetectionHistory
- Связь OneToMany между таблицами
- Полный набор CRUD операций

<div align="center">
  <img src="https://media.giphy.com/media/VbnUQpnihPSIgIXuZv/giphy.gif" width="300" alt="База данных">
</div>

## ⚡ Лабораторная работа 3
**Тема:** Кэширование данных  
**Особенности:**
- Кастомные SQL запросы
- In-memory кэширование
- Оптимизация производительности
- Аннотация @Cacheable

## 🛡️ Лабораторная работа 4
**Тема:** Обработка ошибок  
**Особенности:**
- Глобальный обработчик исключений
- Логирование через Spring AOP
- Swagger документация
- Проверка стиля кода



## 📦 Лабораторная работа 5
**Тема:** Пакетная обработка  
**Особенности:**
- Bulk операции с текстами
- Использование Stream API
- 80% покрытие тестами
- Интеграционные тесты

<div align="center">
  <img src="https://media.giphy.com/media/YS0qrh5xqhsVzt4Iq1/giphy.gif" width="300" alt="Пакетная обработка">
</div>

## 🚀 Быстрый старт

```bash
# 1. Клонировать репозиторий
git clone https://github.com/yourname/language-detector.git

# 2. Настроить подключение к БД
# (файл application.yml)

# 3. Запустить приложение
mvn spring-boot:run
```

## 🌐 Доступные интерфейсы

| Интерфейс | URL | ^=^ |
|-----------|-----|-----|
| REST API | `http://localhost:8080/api` | 🔌  |
| Swagger UI | `http://localhost:8080/swagger-ui.html` | 📚  |
| База данных | `jdbc:postgresql://localhost:5432/langdetect` | 🗄️ |

## 🧪 Тестирование

```bash
# Запуск всех тестов
mvn test

# Генерация отчета о покрытии
mvn jacoco:report
```

**Результаты тестирования:**
- ✅ 80% покрытие кода


<div align="center">
✨ *Создано с любовью к Java* ✨  
<div align="center">
💫 *Звездайте репозиторий, если вам понравилось!* 💫

