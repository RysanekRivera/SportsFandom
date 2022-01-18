package com.rysanek.sportsfandom.data.remote.dtos.scores

import com.rysanek.sportsfandom.data.local.entities.ScoresEntity

data class ScoreDTO(
    val dateEvent: String?,
    val idAwayTeam: String?,
    val idEvent: String?,
    val idHomeTeam: String?,
    val idLeague: String?,
    val idLiveScore: String?,
    val idPlayer: String?,
    val intAwayScore: String?,
    val intEventScore: String?,
    val intEventScoreTotal: String?,
    val intHomeScore: String?,
    val strAwayTeam: String?,
    val strAwayTeamBadge: String?,
    val strEventTime: String?,
    val strHomeTeam: String?,
    val strHomeTeamBadge: String?,
    val strLeague: String?,
    val strPlayer: String?,
    val strProgress: String?,
    val strSport: String?,
    val strStatus: String?,
    val updated: String?
){
    fun toScoreEntity() = ScoresEntity(
        dateEvent,
        idAwayTeam,
        idEvent,
        idHomeTeam,
        idLeague,
        idLiveScore,
        idPlayer,
        intAwayScore,
        intEventScore,
        intEventScoreTotal,
        intHomeScore,
        strAwayTeam,
        strAwayTeamBadge,
        strEventTime,
        strHomeTeam,
        strHomeTeamBadge,
        strLeague,
        strPlayer,
        strProgress,
        strSport,
        strStatus,
        updated
    )
}