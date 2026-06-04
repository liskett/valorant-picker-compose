package com.example.valorantpickercompose.domain.model
// дата класс для хранения рекомендации: нужных ролей для выбора и предлагаемых агентов
data class Recommendation(
    val missingRoles: List<Role>,
    val suggestedByRole: Map<Role, List<AgentInfo>>,
    val topAgent: AgentInfo?
)
