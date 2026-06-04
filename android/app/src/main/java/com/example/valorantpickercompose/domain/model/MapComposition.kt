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
    private val mapSpecific: Map<GameMap, List<Role>> = mapOf(
        GameMap.ASCENT to defaultComp,
        GameMap.BIND to defaultComp,
        GameMap.HAVEN to defaultComp,
        GameMap.SPLIT to defaultComp,
        GameMap.ICEBOX to defaultComp,
        GameMap.BREEZE to defaultComp,
        GameMap.PEARL to defaultComp,
        GameMap.LOTUS to defaultComp,
        GameMap.SUNSET to defaultComp,
        GameMap.FRACTURE to defaultComp,
        GameMap.CORRODE to defaultComp,
        GameMap.ABYSS to defaultComp
    )

    //некий геттер ролей для конкретной карты для доступа извне
    fun desiredRolesForMap(map: GameMap?): List<Role> {
        return mapSpecific[map] ?: defaultComp // в случае опечатки вернет дефолтную композицию
    }
}
