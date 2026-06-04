package com.example.valorantpickercompose.presentation.mapper

import com.example.valorantpickercompose.R

object MedalUiMapper {
    fun getMedal(index: Int) : Int {
        return when (index) {
            0 -> R.drawable.gold_medal
            1 -> R.drawable.silver_medal
            2 -> R.drawable.bronze_medal
            else -> {
                R.drawable.ic_logo
            }
        }
    }
}