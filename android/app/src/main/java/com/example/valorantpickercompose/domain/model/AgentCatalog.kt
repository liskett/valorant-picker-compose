package com.example.valorantpickercompose.domain.model
// единственный объект класса(чтобы не создавать экземпляр класса) - каталог агентов
object AgentCatalog {

    val allAgents: List<AgentInfo> = listOf(
        AgentInfo(Agent.ASTRA, Role.CONTROLLER),
        AgentInfo(Agent.BREACH, Role.INITIATOR),
        AgentInfo(Agent.BRIMSTONE, Role.CONTROLLER),
        AgentInfo(Agent.CHAMBER, Role.SENTINEL),
        AgentInfo(Agent.CLOVE, Role.CONTROLLER),
        AgentInfo(Agent.CYPHER, Role.SENTINEL),
        AgentInfo(Agent.DEADLOCK, Role.SENTINEL),
        AgentInfo(Agent.FADE, Role.INITIATOR),
        AgentInfo(Agent.GEKKO, Role.INITIATOR),
        AgentInfo(Agent.HARBOR, Role.CONTROLLER),
        AgentInfo(Agent.ISO, Role.DUELIST),
        AgentInfo(Agent.JETT, Role.DUELIST),
        AgentInfo(Agent.KAYO, Role.INITIATOR),
        AgentInfo(Agent.KILLJOY, Role.SENTINEL),
        AgentInfo(Agent.MIKS, Role.CONTROLLER),
        AgentInfo(Agent.NEON, Role.DUELIST),
        AgentInfo(Agent.OMEN, Role.CONTROLLER),
        AgentInfo(Agent.PHOENIX, Role.DUELIST),
        AgentInfo(Agent.RAZE, Role.DUELIST),
        AgentInfo(Agent.REYNA, Role.DUELIST),
        AgentInfo(Agent.SAGE, Role.SENTINEL),
        AgentInfo(Agent.SKYE, Role.INITIATOR),
        AgentInfo(Agent.SOVA, Role.INITIATOR),
        AgentInfo(Agent.TEJO, Role.INITIATOR),
        AgentInfo(Agent.VETO, Role.SENTINEL),
        AgentInfo(Agent.VIPER, Role.CONTROLLER),
        AgentInfo(Agent.VYSE, Role.SENTINEL),
        AgentInfo(Agent.WAYLAY, Role.DUELIST),
        AgentInfo(Agent.YORU, Role.DUELIST)
    )

    //свойство для поиска агента по имени и возврата объекта класса AgentInfo
    val byName: Map<Agent, AgentInfo> = allAgents.associateBy { it.name }
}

