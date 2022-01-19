package com.rysanek.sportsfandom.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.navigation.navArgs
import com.bumptech.glide.RequestManager
import com.google.android.play.core.internal.t
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.databinding.ActivityTeamInfoBinding
import com.rysanek.sportsfandom.domain.usecases.LoadTeams
import com.rysanek.sportsfandom.domain.usecases.SearchTeams
import com.rysanek.sportsfandom.domain.utils.fullScreenMode
import com.rysanek.sportsfandom.domain.utils.gone
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeamInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamInfoBinding
    @Inject
    lateinit var teams: LoadTeams
    @Inject
    lateinit var search: SearchTeams
    private val args by navArgs<TeamInfoActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreenMode()

        binding = ActivityTeamInfoBinding.inflate(layoutInflater)

        val teamBadge = args.teamEntity.strTeamBadge
        Log.d("TeamsInfoActivity", "team badge: $teamBadge")
        loadImage(teamBadge, binding.ivTeamBadge)

        binding.tvTitle.text = args.teamEntity.strTeam ?: ""
        binding.tvFormedInfo.text = args.teamEntity.intFormedYear ?: ""
        binding.tvCountryInfo.text = args.teamEntity.strCountry ?: ""
        binding.tvSportTypeInfo.text = args.teamEntity.strSport ?: ""
        binding.tvDescriptionInfo.text = args.teamEntity.strDescriptionEN ?: ""

        val leagues = listOf(
            args.teamEntity.strLeague,
            args.teamEntity.strLeague2,
            args.teamEntity.strLeague3,
            args.teamEntity.strLeague4,
            args.teamEntity.strLeague5,
            args.teamEntity.strLeague6,
            args.teamEntity.strLeague7
        )

        binding.tvLeaguesInfo.text = returnLeaguesStr(leagues)

        loadImage(args.teamEntity.strTeamJersey, binding.ivTeamJersey)
        loadImage(args.teamEntity.strTeamLogo, binding.ivTeamLogo)
        loadImage(args.teamEntity.strTeamFanart1, binding.ivTeamArt1)
        loadImage(args.teamEntity.strTeamFanart2, binding.ivTeamArt2)
        loadImage(args.teamEntity.strTeamFanart2, binding.ivTeamArt3)
        loadImage(args.teamEntity.strTeamFanart4, binding.ivTeamArt4)
        loadImage(args.teamEntity.strTeamBanner, binding.ivTeamBanner)

        setContentView(binding.root)
    }

    private fun loadImage(url: String?, imageView: ImageView) {
        if (!url.isNullOrEmpty()) teams.loadImagesToView(url, imageView)
        else imageView.gone()
    }

    private fun returnLeaguesStr(leagues: List<String?>): String {
        var newStr = ""
        leagues.forEach {
            if (!it.isNullOrEmpty()) newStr += if (newStr == "") it else ", $it"
        }

        return newStr
    }

}