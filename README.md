# Valorant Picker

Небольшое Android‑приложение на Jetpack Compose, которое помогает подобрать оптимальных агентов под карту и текущий пик команды, учитывая статистику и (в будущем) личные навыки игрока.

## Возможности

- выбор карты из актуального пула (Ascent, Bind, Haven, Icebox и др.)
- выбор до 4 агентов для своей команды
- экран рекомендаций с подсказками, кого лучше взять в оставшиеся слоты
- авторизация и регистрация пользователей
- хранение пользователей в PostgreSQL через FastAPI backend
- кастомная Valorant‑стилистика (цвета, шрифты, иконки агентов и карт)

## Архитектура

Проект построен по подходу, близкому к **Clean Architecture**:

- **data**  
  - `AuthApi`, `RetrofitClient` — работа с HTTP API (FastAPI)  
  - `AuthRepositoryImpl` — реализация репозитория авторизации  
  - `MapAgentStatsRepository` — чтение и парсинг `map_agent_stats.json` из `assets`
- **domain**  
  - модели: `User`, `AgentInfo`, `Role`, `Recommendation`, `MapComposition`, `AgentCatalog`  
  - интерфейс `AuthRepository`  
  - use case‑ы: `LoginUseCase`, `RegisterUseCase`, `RecommendAgentsUseCase`
- **presentation (android)**  
  - `HomeScreen`, `SignInScreen`, `SignUpScreen`, `ChooseMapScreen`, `AgentScreen`, `ResultScreen`  
  - `AuthViewModel`, `PickerViewModel`, `AgentViewModel`  
  - навигация через `NavHost` и `ScreenRoutes`
- **backend**  
  - FastAPI‑приложение `main.py`  
  - PostgreSQL‑таблица `users`  
  - эндпоинты `/register` и `/login`

## Фронтенд (Android)

### Технологии

- Kotlin, Coroutines
- Jetpack Compose
- Navigation Compose
- Retrofit + OkHttp (логирование запросов)
- Gson (парсинг JSON‑файла с картами/агентами)
- kotlinx.coroutines StateFlow для управления состоянием
- кастомная тема в стиле Valorant

### Основные экраны

- **HomeScreen**  
  Стартовый экран, логотип, краткое описание и кнопка входа.

- **SignInScreen / SignUpScreen**  
  Экраны входа и регистрации с текстовыми полями и кнопками.  
  При успешном логине/регистрации пользователь переводится дальше по навигации.

- **ChooseMapScreen**  
  Выбор карты, каждая карта — карточка с изображением и подсветкой выбранного варианта.

- **AgentScreen**  
  Сетка иконок агентов (LazyVerticalGrid), выбор до 4 персонажей, подсветка выбранных, отображение текущего пика в отдельном ряду.

- **ResultScreen**  
  Показывает:
  - выбранную карту  
  - текущий пик команды  
  - блок **RECOMMENDED PICKS** с рекомендуемыми агентами и их статистикой (роль, WR, PR)

## Логика рекомендаций

Рекомендации считаются в `RecommendAgentsUseCase`:

1. Для выбранной карты определяется желаемая композиция ролей (`MapComposition`).
2. По текущему пику считается, какие роли ещё не закрыты.
3. Из `AgentCatalog` отбираются агенты нужных ролей, которые ещё не выбраны игроком.
4. Для каждого агента рассчитывается «скор» на основе winrate и pickrate:  
   используется статистика по карте из `MapAgentStatsRepository` (если есть), иначе — базовые значения из `AgentCatalog`.
5. Агенты сортируются по скору и возвращаются как список рекомендаций.

Статистика по картам и агентам хранится локально в `assets/map_agent_stats.json`.

## Бэкенд

### Технологии

- Python 3
- FastAPI
- SQLAlchemy
- PostgreSQL
- хэширование паролей через SHA‑256

### Структура

`main.py`:

- модель `User` (SQLAlchemy)
- Pydantic‑модели: `RegisterRequest`, `LoginRequest`, `ResponseMessage`
- эндпоинты:
  - `POST /register` — регистрация пользователя, проверка уникальности email, сохранение хэша пароля
  - `POST /login` — проверка существования пользователя и корректности пароля
- подключение к БД через `DATABASE_URL = postgresql://...`
- создание таблицы `users`, если её ещё нет

SQL для таблицы:

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
## Как запустить
### 1. Backend (FastAPI + PostgreSQL)
1.1. Подготовка окружения
Установи Python 3.10+.

Создай и активируй виртуальное окружение (по желанию):

```bash
python -m venv venv
source venv/bin/activate      # Linux / macOS
venv\Scripts\activate         # Windows
```
Установи зависимости:

```bash
pip install fastapi uvicorn sqlalchemy psycopg2-binary
```
1.2. Настройка PostgreSQL
Установи PostgreSQL (если ещё не стоит).

Создай базу данных, например valorant_app:

```sql
CREATE DATABASE valorant_app;
```
Убедись, что пользователь/пароль совпадают с DATABASE_URL в main.py:

```python
DATABASE_URL = "postgresql://postgres:1234@localhost:5432/valorant_app"
```
При необходимости измени логин/пароль/имя базы под свою установку.

Выполни SQL для таблицы пользователей (если хочешь руками, иначе её создаст SQLAlchemy):

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email TEXT UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
1.3. Запуск сервера
Из папки с main.py:

```bash
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```
После запуска backend должен отвечать, например:

http://localhost:8000/docs — Swagger UI

POST /register, POST /login — эндпоинты для приложения

Для Android‑эмулятора этот же сервер доступен по http://10.0.2.2:8000/.

## 2. Android‑приложение
2.1. Подготовка проекта
Открой проект в Android Studio.

Убедись, что в RetrofitClient указан тот же адрес, что и у backend:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8000/"
```
для эмулятора: 10.0.2.2

для реального устройства в одной сети с ПК: IP‑адрес твоего ПК, например
http://192.168.0.10:8000/ (и тогда нужно поменять BASE_URL).

Проверь, что файл map_agent_stats.json лежит в app/src/main/assets/ и имя совпадает:

```kotlin
context.assets.open("map_agent_stats.json")
```
2.2. Запуск приложения
Запусти backend (см. шаг 1.3).

В Android Studio:

выбери конфигурацию app

выбери эмулятор или физическое устройство

нажми Run.

В приложении:

на HomeScreen нажми SIGN IN

при необходимости создай аккаунт на SIGN UP

после успешного логина попадёшь на выбор карты, затем агентов, затем экран рекомендаций.

## План на будущее
создать экран для ввода пользователем «умения» играть на каждом агенте (ползунок 0–100 рядом с иконкой), учитывать эти значения в скоринге рекомендаций

добавить небольшой опрос стиля игры и использовать его как дополнительные веса при ранжировании

сохранять профиль игрока локально (скиллы, стиль, любимые карты) для персональных рекомендаций между сессиями

расширить MapComposition под реальные мета‑сетапы для каждой карты

экспериментировать с более продвинутой ML‑логикой подбора агентов (учёт синергии, банов/пиков и т.п.)
