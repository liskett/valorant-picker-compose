# Valorant Picker

Небольшое Android-приложение на Jetpack Compose, которое помогает подобрать оптимальных агентов Valorant под выбранную карту и текущий пик команды. Рекомендации строятся на основе статистики агентов, выбранных ролей, карты и пользовательских настроек приоритета: winrate, pickrate или balanced.

## Возможности

- выбор карты из списка доступных карт
- выбор до 4 агентов для своей команды
- экран рекомендаций с лучшими вариантами для оставшихся слотов
- отображение рекомендуемых агентов с ролью, winrate, pickrate и итоговым скором
- авторизация и регистрация пользователей
- подключение Android-приложения к FastAPI backend
- хранение пользователей в PostgreSQL
- локальное кэширование статистики агентов через Room
- сохранение пользовательских настроек через DataStore
- экран настроек с выбором приоритета рекомендаций
- поддержка локализации через string resources
- кастомная Valorant-стилистика: цвета, шрифты, иконки агентов и изображения карт

## Архитектура

Проект построен по подходу, близкому к Clean Architecture, с разделением на слои:

- **data**

  Работа с сетью, локальным хранилищем, базой данных и источниками данных.

  - `KtorClient` — настройка HTTP-клиента для запросов к backend
  - `AuthService` — запросы авторизации и регистрации
  - `AuthRepositoryImpl` — реализация репозитория авторизации
  - `MapAgentStatsDataSource` — чтение `map_agent_stats.json` из assets
  - `StatsRepositoryImpl` — работа со статистикой агентов и её кэшированием
  - `RecommendationRepositoryImpl` — расчёт и выдача рекомендаций
  - `SettingsRepositoryImpl` — сохранение и чтение настроек пользователя
  - `AppDatabase`, `AgentStatsDao`, `AgentStatsEntity` — Room-база для локальной статистики
  - `DataStore` — хранение настроек и версии статистики

- **domain**

  Бизнес-логика приложения.

  - модели: `User`, `Agent`, `GameMap`, `Role`, `Recommendation`, `RecommendationPriority`
  - интерфейсы репозиториев: `AuthRepository`, `StatsRepository`, `RecommendationRepository`, `SettingsRepository`
  - use case-ы: `LoginUseCase`, `RegisterUseCase`, `RecommendAgentsUseCase`

- **presentation**

  UI, состояние экранов, ViewModel и навигация.

  - экраны: `HomeScreen`, `SignInScreen`, `SignUpScreen`, `ChooseMapScreen`, `AgentScreen`, `ResultScreen`, `SettingsScreen`
  - ViewModel: `AuthViewModel`, `PickerViewModel`, `SettingsViewModel`
  - state-классы: `PickerState`, `ResultUiState`
  - mapper-классы: `AgentUiMapper`, `MapUiMapper`, `MedalUiMapper`
  - фабрики ViewModel: `AuthViewModelFactory`, `PickerViewModelFactory`, `SettingsViewModelFactory`

- **di / app module**

  Сборка зависимостей приложения.

  - `AppModule` создаёт репозитории, use case-ы, базу данных, DataStore и фабрики ViewModel.

- **backend**

  Серверная часть проекта.

  - FastAPI-приложение
  - PostgreSQL-таблица `users`
  - эндпоинты `/register` и `/login`

## Фронтенд Android

### Технологии

- Kotlin
- Jetpack Compose
- Navigation Compose
- Coroutines
- StateFlow
- Ktor Client
- kotlinx.serialization
- Room
- DataStore
- KSP
- кастомная Compose-тема в стиле Valorant

### Основные экраны

- **HomeScreen**

  Стартовый экран приложения с логотипом, кратким описанием и переходом к авторизации. Также добавлен переход на экран настроек.

- **SignInScreen / SignUpScreen**

  Экраны входа и регистрации. Пользователь вводит email и пароль, после успешного входа или регистрации приложение переводит его дальше по навигации.

- **ChooseMapScreen**

  Экран выбора карты. Карты отображаются карточками с изображениями и подсветкой выбранного варианта.

- **AgentScreen**

  Экран выбора агентов. Агенты отображаются в сетке через `LazyVerticalGrid`. Пользователь может выбрать до 4 агентов, выбранные агенты подсвечиваются, а текущий пик отображается отдельно.

- **ResultScreen**

  Экран результата и рекомендаций. Показывает выбранную карту, текущий пик команды, top agent, список рекомендуемых агентов, роль агента, winrate, pickrate и итоговый скор.

  Также экран обрабатывает состояния загрузки, ошибки и успешного получения рекомендаций.

- **SettingsScreen**

  Экран настроек приложения. Сейчас реализован выбор приоритета рекомендаций:

  - `WIN_RATE_PRIORITY` — большее влияние winrate
  - `PICK_RATE_PRIORITY` — большее влияние pickrate
  - `BALANCED` — сбалансированный расчёт

  Выбранная настройка сохраняется локально через DataStore и используется при расчёте рекомендаций.

## Логика выбора карты и агентов

За выбор карты и агентов отвечает `PickerViewModel`.

Состояние хранится в `PickerState`:

```kotlin
data class PickerState(
    val selectedMap: GameMap? = null,
    val selectedAgents: List<Agent> = emptyList()
)
```

Теперь вся логика выбора объединена в одном ViewModel:

- выбор карты
- выбор агента
- удаление агента
- ограничение выбора до 4 агентов
- загрузка рекомендаций
- хранение состояния для экранов выбора и результата

Благодаря этому больше не нужно передавать несколько ViewModel между экранами.

## Логика рекомендаций

Рекомендации строятся через `RecommendAgentsUseCase` и `RecommendationRepositoryImpl`.

Общий принцип:

1. Пользователь выбирает карту.
2. Пользователь выбирает до 4 агентов своей команды.
3. Для выбранной карты определяется желаемая композиция ролей.
4. По текущему пику команды считаются недостающие роли.
5. Из каталога агентов выбираются персонажи нужных ролей, которых ещё нет в пике.
6. Для каждого агента берётся статистика по выбранной карте: winrate и pickrate.
7. На основе выбранного в настройках приоритета считается итоговый score.
8. Агенты сортируются по score и отображаются на `ResultScreen`.

### Приоритеты рекомендаций

В настройках можно выбрать один из трёх режимов:

- **Winrate priority**

```kotlin
score = winrate
```

- **Pickrate priority**

```kotlin
score = pickrate
```

- **Balanced**

```kotlin
score = winrate * 0.7 + pickrate * 0.3
```

Balanced-режим используется как более универсальный вариант, где winrate важнее, но pickrate тоже влияет на итоговую рекомендацию.

## Работа со статистикой

Статистика агентов по картам хранится в файле:

```text
app/src/main/assets/map_agent_stats.json
```

В текущей версии статистика больше не читается напрямую при каждом обращении.

Теперь процесс такой:

1. `MapAgentStatsDataSource` читает JSON из assets.
2. `StatsRepositoryImpl` проверяет версию статистики.
3. Если данные ещё не закэшированы или версия изменилась, статистика сохраняется в Room.
4. Дальше рекомендации используют данные из локальной Room-базы.

Для хранения используются:

- `AppDatabase`
- `AgentStatsDao`
- `AgentStatsEntity`

Версия статистики сохраняется через DataStore. Это нужно, чтобы при обновлении `map_agent_stats.json` приложение могло обновить локальный кэш.

## Авторизация

Авторизация реализована через backend на FastAPI.

Android-приложение отправляет запросы через Ktor:

- `POST /register`
- `POST /login`

За авторизацию отвечают:

- `KtorClient`
- `AuthService`
- `AuthRepositoryImpl`
- `LoginUseCase`
- `RegisterUseCase`
- `AuthViewModel`

Ошибки backend обрабатываются отдельно. Для этого добавлены:

- `ApiErrorResponse`
- `AuthException`

Например, приложение может корректно обработать ситуации:

- пользователь уже существует
- пользователь не найден
- неверный пароль
- ошибка сервера
- проблема с подключением

## Бэкенд

### Технологии

- Python 3
- FastAPI
- SQLAlchemy
- PostgreSQL
- SHA-256 для хэширования паролей

### Основная логика

Backend отвечает за регистрацию и вход пользователей.

Реализованы эндпоинты:

- `POST /register` — регистрирует пользователя, проверяет уникальность email и сохраняет хэш пароля
- `POST /login` — проверяет существование пользователя и правильность пароля, после чего возвращает данные пользователя

### Таблица пользователей

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Локализация

В проект добавлены string resources:

```text
values/strings.xml
values-ru/strings.xml
values-en/strings.xml
```

Часть текста интерфейса уже вынесена из кода в ресурсы. Это подготовка к полноценной мультиязычности приложения.

В будущем можно добавить переключатель языка в `SettingsScreen`.

## Ресурсы

В проекте используются:

- иконки агентов
- full-art изображения агентов
- изображения карт
- кастомные шрифты
- иконки для ResultScreen

Добавлены UI-мапперы:

- `AgentUiMapper` — связывает enum агента с его иконкой и изображением
- `MapUiMapper` — связывает enum карты с изображением карты
- `MedalUiMapper` — отвечает за отображение медалей/иконок результата

## Как запустить

### 1. Backend

#### 1.1. Подготовка окружения

Установи Python 3.10+.

Создай и активируй виртуальное окружение:

```bash
python -m venv venv
```

Linux / macOS:

```bash
source venv/bin/activate
```

Windows:

```bash
venv\Scripts\activate
```

Установи зависимости:

```bash
pip install fastapi uvicorn sqlalchemy psycopg2-binary
```

#### 1.2. Настройка PostgreSQL

Создай базу данных, например:

```sql
CREATE DATABASE valorant_app;
```

Проверь строку подключения к базе данных в backend:

```python
DATABASE_URL = "postgresql://postgres:1234@localhost:5432/valorant_app"
```

При необходимости измени логин, пароль, host, port или название базы под свою локальную установку PostgreSQL.

#### 1.3. Запуск сервера

Из папки с backend-файлом запусти:

```bash
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

После запуска Swagger UI будет доступен по адресу:

```text
http://localhost:8000/docs
```

Для Android-эмулятора backend доступен по адресу:

```text
http://10.0.2.2:8000/
```

Для реального устройства нужно использовать IP-адрес компьютера в локальной сети, например:

```text
http://192.168.0.10:8000/
```

### 2. Android-приложение

#### 2.1. Подготовка проекта

Открой проект в Android Studio.

Проверь base URL backend в Ktor-клиенте или сервисе авторизации.

Для эмулятора:

```kotlin
http://10.0.2.2:8000/
```

Для физического устройства:

```kotlin
http://192.168.0.10:8000/
```

Также проверь, что `map_agent_stats.json` находится по пути:

```text
app/src/main/assets/map_agent_stats.json
```

#### 2.2. HTTP-доступ к локальному backend

Для работы с локальным backend по HTTP добавлен `network_security_config.xml`.

В `AndroidManifest.xml` должны быть указаны:

```xml
android:networkSecurityConfig="@xml/network_security_config"
android:usesCleartextTraffic="true"
```

Это нужно для запуска приложения с локальным сервером без HTTPS.

#### 2.3. Запуск приложения

1. Запусти backend.
2. Открой Android-проект в Android Studio.
3. Выбери конфигурацию `app`.
4. Выбери эмулятор или физическое устройство.
5. Нажми `Run`.

В приложении:

1. Перейди на экран входа.
2. Зарегистрируйся или войди в аккаунт.
3. Выбери карту.
4. Выбери до 4 агентов.
5. Перейди на экран результата.
6. Посмотри рекомендации.

## Текущий статус проекта

На данный момент реализованы:

- базовая навигация
- авторизация и регистрация
- backend на FastAPI
- PostgreSQL для пользователей
- выбор карты
- выбор агентов
- единый `PickerViewModel`
- рекомендации по агентам
- Room-кэширование статистики
- DataStore для настроек
- экран настроек
- выбор приоритета рекомендаций
- ResultScreen с top agent и списком рекомендаций
- базовая локализация
- разделение проекта на data / domain / presentation

## План на будущее

- добавить экран ввода личного уровня игры на каждом агенте с ползунком от 0 до 100
- учитывать личный skill игрока в итоговом score рекомендации
- добавить Room-таблицы для хранения пользовательского профиля
- добавить опрос стиля игры:
  - агрессивный / осторожный
  - solo / teamplay
  - entry / support
  - любовь к сетапам, ультам, флешкам и т.п.
- учитывать стиль игры при подборе агентов
- добавить полноценное переключение языка в SettingsScreen
- расширить настройки приложения
- улучшить визуальный стиль ResultScreen
- добавить более подробное объяснение, почему рекомендован конкретный агент
- расширить статистику по картам и агентам
- улучшить формулу скоринга
- добавить ML-логику подбора агентов
- обновить дизайн экранов под более цельный Valorant-style
- оптимизировать ресурсы изображений и загрузку full-art агентов
