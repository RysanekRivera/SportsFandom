package com.rysanek.sportsfandom.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rysanek.sportsfandom.domain.utils.Constants.SEARCH_TABLE
import com.rysanek.sportsfandom.domain.utils.Constants.TEAMS_TABLE

@Entity(tableName = TEAMS_TABLE)
data class TeamEntity(
    val idAPIfootball: String?,
    val idLeague: String?,
    val idLeague2: String?,
    val idLeague3: String?,
    val idLeague4: String?,
    val idLeague5: String?,
    val idLeague6: String?,
    val idLeague7: String?,
    val idSoccerXML: String?,
    val idTeam: String?,
    val intFormedYear: String?,
    val intLoved: String?,
    val intStadiumCapacity: String?,
    val strAlternate: String?,
    val strCountry: String?,
    val strDescriptionCN: String?,
    val strDescriptionDE: String?,
    val strDescriptionEN: String?,
    val strDescriptionES: String?,
    val strDescriptionFR: String?,
    val strDescriptionHU: String?,
    val strDescriptionIL: String?,
    val strDescriptionIT: String?,
    val strDescriptionJP: String?,
    val strDescriptionNL: String?,
    val strDescriptionNO: String?,
    val strDescriptionPL: String?,
    val strDescriptionPT: String?,
    val strDescriptionRU: String?,
    val strDescriptionSE: String?,
    val strDivision: String?,
    val strFacebook: String?,
    val strGender: String?,
    val strInstagram: String?,
    val strKeywords: String?,
    val strLeague: String?,
    val strLeague2: String?,
    val strLeague3: String?,
    val strLeague4: String?,
    val strLeague5: String?,
    val strLeague6: String?,
    val strLeague7: String?,
    val strLocked: String?,
    val strManager: String?,
    val strRSS: String?,
    val strSport: String?,
    val strStadium: String?,
    val strStadiumDescription: String?,
    val strStadiumLocation: String?,
    val strStadiumThumb: String?,
    @ColumnInfo(name = "strTeam") val strTeam: String?,
    val strTeamBadge: String?,
    val strTeamBanner: String?,
    val strTeamFanart1: String?,
    val strTeamFanart2: String?,
    val strTeamFanart3: String?,
    val strTeamFanart4: String?,
    val strTeamJersey: String?,
    val strTeamLogo: String?,
    val strTeamShort: String?,
    val strTwitter: String?,
    val strWebsite: String?,
    val strYoutube: String?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}
