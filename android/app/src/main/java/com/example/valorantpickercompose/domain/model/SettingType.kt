package com.example.valorantpickercompose.domain.model

enum class SettingType {
    WIN_RATE_PRIORITY,    // приоритет на винрейт
    PICK_RATE_PRIORITY,   // приоритет на пикрейт
    BALANCED              // сбалансированный (wr*0.7 + pr*0.3)
}