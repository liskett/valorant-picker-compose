package com.example.valorantpickercompose.domain.model
// объект для хранения данных о том, какая синергия ролей "в мете"(какие роли нужны) на конкретной карте
object MapComposition {

    private val defaultComp = listOf(
        Role.CONTROLLER,
        Role.SENTINEL,
        Role.INITIATOR,
        Role.DUELIST,
        Role.DUELIST
    )
    //пока что на всех картах стоит дефолтная композиция: 2 дуэлянта, 1 инициатор, 1 страж и 1 контроллер, но этот мап создан для того, чтобы менять в случае смены меты
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

    //некий геттер ролей для конкретной карты для доступа извне
    fun desiredRolesForMap(mapName: String): List<Role> {
        return mapSpecific[mapName.uppercase()] ?: defaultComp // в случае опечатки вернет дефолтную композицию
    }
}
