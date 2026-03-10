package com.example.valorantpickercompose.domain.model

object AgentCatalog {

    val allAgents: List<AgentInfo> = listOf(
        AgentInfo("Astra",    Role.CONTROLLER, winRate = 0.49, pickRate = 0.08),
        AgentInfo("Breach",   Role.INITIATOR,  winRate = 0.51, pickRate = 0.10),
        AgentInfo("Brimstone",Role.CONTROLLER, winRate = 0.52, pickRate = 0.12),
        AgentInfo("Chamber",  Role.SENTINEL,   winRate = 0.50, pickRate = 0.09),
        AgentInfo("Clove",    Role.CONTROLLER, winRate = 0.53, pickRate = 0.14),
        AgentInfo("Cypher",   Role.SENTINEL,   winRate = 0.51, pickRate = 0.11),
        AgentInfo("Deadlock", Role.SENTINEL,   winRate = 0.48, pickRate = 0.05),
        AgentInfo("Fade",     Role.INITIATOR,  winRate = 0.52, pickRate = 0.12),
        AgentInfo("Gekko",    Role.INITIATOR,  winRate = 0.55, pickRate = 0.15),
        AgentInfo("Harbor",   Role.CONTROLLER, winRate = 0.50, pickRate = 0.07),
        AgentInfo("Iso",      Role.DUELIST,    winRate = 0.49, pickRate = 0.06),
        AgentInfo("Jett",     Role.DUELIST,    winRate = 0.51, pickRate = 0.20),
        AgentInfo("Kayo",     Role.INITIATOR,  winRate = 0.50, pickRate = 0.10),
        AgentInfo("Killjoy",  Role.SENTINEL,   winRate = 0.54, pickRate = 0.18),
        AgentInfo("Neon",     Role.DUELIST,    winRate = 0.52, pickRate = 0.11),
        AgentInfo("Omen",     Role.CONTROLLER, winRate = 0.52, pickRate = 0.16),
        AgentInfo("Phoenix",  Role.DUELIST,    winRate = 0.53, pickRate = 0.13),
        AgentInfo("Raze",     Role.DUELIST,    winRate = 0.54, pickRate = 0.19),
        AgentInfo("Reyna",    Role.DUELIST,    winRate = 0.50, pickRate = 0.22),
        AgentInfo("Sage",     Role.SENTINEL,   winRate = 0.51, pickRate = 0.21),
        AgentInfo("Skye",     Role.INITIATOR,  winRate = 0.53, pickRate = 0.14),
        AgentInfo("Sova",     Role.INITIATOR,  winRate = 0.52, pickRate = 0.17),
        AgentInfo("Tejo",     Role.INITIATOR,  winRate = 0.48, pickRate = 0.04),
        AgentInfo("Veto",     Role.SENTINEL,   winRate = 0.47, pickRate = 0.03),
        AgentInfo("Viper",    Role.CONTROLLER, winRate = 0.55, pickRate = 0.18),
        AgentInfo("Vyse",     Role.SENTINEL,   winRate = 0.52, pickRate = 0.09),
        AgentInfo("Waylay",   Role.DUELIST,    winRate = 0.49, pickRate = 0.05),
        AgentInfo("Yoru",     Role.DUELIST,    winRate = 0.49, pickRate = 0.07)
    )

    val byName: Map<String, AgentInfo> = allAgents.associateBy { it.name }
}
