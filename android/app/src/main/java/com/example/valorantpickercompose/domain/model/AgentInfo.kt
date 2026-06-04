package com.example.valorantpickercompose.domain.model
// дата класс для хранения имени и роли агента для дальнейшего использования в каталоге агентов, то есть для создания объектов этого класса - агентов
data class AgentInfo(
    val name: Agent,
    val role: Role
)
