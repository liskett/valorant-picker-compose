package com.example.valorantpickercompose.domain.model

data class Recommendation(
    val missingRoles: List<Role>,
    val suggestedAgents: List<AgentInfo>
)
