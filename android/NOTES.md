# пометки по Valorant Picker

## цепочка аутентификации
1. LoginRequest / RegisterRequest / AuthResponse - модели данных
2. AuthApi - интерфейс Retrofit
3. RetrofitClient - создание HTTP-клиента
4. User - доменная модель
5. AuthRepository (интерфейс) - абстракция
6. AuthRepositoryImpl - реализация
7. LoginUseCase / RegisterUseCase - бизнес-логика
8. AuthModule - DI (сборка)
9. AuthViewModel - управление состоянием