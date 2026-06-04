package com.example.valorantpickercompose.presentation.mapper

import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.GameMap

object MapUiMapper {

    fun getIcon(map: GameMap): Int {
        return when (map) {
            GameMap.CORRODE -> R.drawable.corrode_map_icon
            GameMap.SUNSET -> R.drawable.sunset_map_icon
            GameMap.LOTUS -> R.drawable.lotus_map_icon
            GameMap.PEARL -> R.drawable.pearl_map_icon
            GameMap.FRACTURE -> R.drawable.fracture_map_icon
            GameMap.BREEZE -> R.drawable.breeze_map_icon
            GameMap.ICEBOX -> R.drawable.icebox_map_icon
            GameMap.ASCENT -> R.drawable.ascent_map_icon
            GameMap.SPLIT -> R.drawable.split_map_icon
            GameMap.HAVEN -> R.drawable.haven_map_icon
            GameMap.BIND -> R.drawable.bind_map_icon
            GameMap.ABYSS -> R.drawable.abyss_map_icon
        }
    }
}