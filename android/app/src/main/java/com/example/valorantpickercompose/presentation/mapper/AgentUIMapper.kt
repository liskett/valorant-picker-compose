package com.example.valorantpickercompose.presentation.mapper

import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.Agent

object AgentUiMapper {

    fun getIcon(agent: Agent): Int {
        return when (agent) {
            Agent.ASTRA -> R.drawable.astra_icon
            Agent.BREACH -> R.drawable.breach_icon
            Agent.BRIMSTONE -> R.drawable.brimstone_icon
            Agent.CHAMBER -> R.drawable.chamber_icon
            Agent.CLOVE -> R.drawable.clove_icon
            Agent.CYPHER -> R.drawable.cypher_icon
            Agent.DEADLOCK -> R.drawable.deadlock_icon
            Agent.FADE -> R.drawable.fade_icon
            Agent.GEKKO -> R.drawable.gekko_icon
            Agent.HARBOR -> R.drawable.harbor_icon
            Agent.ISO -> R.drawable.iso_icon
            Agent.JETT -> R.drawable.jett_icon
            Agent.KAYO -> R.drawable.kayo_icon
            Agent.KILLJOY -> R.drawable.killjoy_icon
            Agent.MIKS -> R.drawable.miks_icon
            Agent.NEON -> R.drawable.neon_icon
            Agent.OMEN -> R.drawable.omen_icon
            Agent.PHOENIX -> R.drawable.phoenix_icon
            Agent.RAZE -> R.drawable.raze_icon
            Agent.REYNA -> R.drawable.reyna_icon
            Agent.SAGE -> R.drawable.sage_icon
            Agent.SKYE -> R.drawable.skye_icon
            Agent.SOVA -> R.drawable.sova_icon
            Agent.TEJO -> R.drawable.tejo_icon
            Agent.VETO -> R.drawable.veto_icon
            Agent.VIPER -> R.drawable.viper_icon
            Agent.VYSE -> R.drawable.vyse_icon
            Agent.WAYLAY -> R.drawable.waylay_icon
            Agent.YORU -> R.drawable.yoru_icon
        }
    }

    fun getArtwork(agent: Agent?): Int {
        return when (agent) {
            Agent.ASTRA -> R.drawable.astra_artwork
            Agent.BREACH -> R.drawable.breach_artwork
            Agent.BRIMSTONE -> R.drawable.brimstone_artwork
            Agent.CHAMBER -> R.drawable.chamber_artwork
            Agent.CLOVE -> R.drawable.clove_artwork
            Agent.CYPHER -> R.drawable.cypher_artwork
            Agent.DEADLOCK -> R.drawable.deadlock_artwork
            Agent.FADE -> R.drawable.fade_artwork
            Agent.GEKKO -> R.drawable.gekko_artwork
            Agent.HARBOR -> R.drawable.harbor_artwork
            Agent.ISO -> R.drawable.iso_artwork
            Agent.JETT -> R.drawable.jett_artwork
            Agent.KAYO -> R.drawable.kayo_artwork
            Agent.KILLJOY -> R.drawable.killjoy_artwork
            Agent.MIKS -> R.drawable.miks_artwork
            Agent.NEON -> R.drawable.neon_artwork
            Agent.OMEN -> R.drawable.omen_artwork
            Agent.PHOENIX -> R.drawable.phoenix_artwork
            Agent.RAZE -> R.drawable.raze_artwork
            Agent.REYNA -> R.drawable.reyna_artwork
            Agent.SAGE -> R.drawable.sage_artwork
            Agent.SKYE -> R.drawable.skye_artwork
            Agent.SOVA -> R.drawable.sova_artwork
            Agent.TEJO -> R.drawable.tejo_artwork
            Agent.VETO -> R.drawable.veto_artwork
            Agent.VIPER -> R.drawable.viper_artwork
            Agent.VYSE -> R.drawable.vyse_artwork
            Agent.WAYLAY -> R.drawable.waylay_artwork
            Agent.YORU -> R.drawable.yoru_artwork
            else -> {
                R.drawable.clove_artwork
            }
        }
    }
}