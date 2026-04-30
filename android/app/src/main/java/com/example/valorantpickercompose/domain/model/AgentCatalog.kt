package com.example.valorantpickercompose.domain.model
// единственный объект класса(чтобы не создавать экземпляр класса) - каталог агентов
object AgentCatalog {

    val allAgents: List<AgentInfo> = listOf(
        AgentInfo("Astra",    Role.CONTROLLER),
        AgentInfo("Breach",   Role.INITIATOR),
        AgentInfo("Brimstone",Role.CONTROLLER),
        AgentInfo("Chamber",  Role.SENTINEL),
        AgentInfo("Clove",    Role.CONTROLLER),
        AgentInfo("Cypher",   Role.SENTINEL),
        AgentInfo("Deadlock", Role.SENTINEL),
        AgentInfo("Fade",     Role.INITIATOR),
        AgentInfo("Gekko",    Role.INITIATOR),
        AgentInfo("Harbor",   Role.CONTROLLER),
        AgentInfo("Iso",      Role.DUELIST),
        AgentInfo("Jett",     Role.DUELIST),
        AgentInfo("KAY/O",     Role.INITIATOR),
        AgentInfo("Killjoy",  Role.SENTINEL),
        AgentInfo("Neon",     Role.DUELIST),
        AgentInfo("Omen",     Role.CONTROLLER),
        AgentInfo("Phoenix",  Role.DUELIST),
        AgentInfo("Raze",     Role.DUELIST),
        AgentInfo("Reyna",    Role.DUELIST),
        AgentInfo("Sage",     Role.SENTINEL),
        AgentInfo("Skye",     Role.INITIATOR),
        AgentInfo("Sova",     Role.INITIATOR),
        AgentInfo("Tejo",     Role.INITIATOR),
        AgentInfo("Veto",     Role.SENTINEL),
        AgentInfo("Viper",    Role.CONTROLLER),
        AgentInfo("Vyse",     Role.SENTINEL),
        AgentInfo("Waylay",   Role.DUELIST),
        AgentInfo("Yoru",     Role.DUELIST)
    )

    //свойство для поиска агента по имени и возврата объекта класса AgentInfo
    val byName: Map<String, AgentInfo> = allAgents.associateBy { it.name }
}

