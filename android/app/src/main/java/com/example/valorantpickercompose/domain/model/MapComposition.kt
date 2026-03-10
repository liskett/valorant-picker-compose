package com.example.valorantpickercompose.domain.model

object MapComposition {

    // очень базовые сетапы, потом можно тонко настроить под каждую карту
    private val defaultComp = listOf(
        Role.CONTROLLER,
        Role.SENTINEL,
        Role.INITIATOR,
        Role.DUELIST,
        Role.DUELIST
    )

    private val mapSpecific: Map<String, List<Role>> = mapOf(
        "ASCENT" to defaultComp,
        "BIND" to defaultComp,
        "HAVEN" to defaultComp,
        "SPLIT" to defaultComp,
        "ICEBOX" to defaultComp,
        "BREEZE" to defaultComp,
        "PEARL" to defaultComp,
        "LOTUS" to defaultComp,
        "SUNSET" to defaultComp,
        "FRACTURE" to defaultComp,
        "CORRODE" to defaultComp,
        "ABYSS" to defaultComp
    )

    fun desiredRolesForMap(mapName: String): List<Role> {
        return mapSpecific[mapName.uppercase()] ?: defaultComp
    }
}
