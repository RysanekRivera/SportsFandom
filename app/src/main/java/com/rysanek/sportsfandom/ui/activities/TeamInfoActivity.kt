package com.rysanek.sportsfandom.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.gms.ads.AdRequest
import com.rysanek.sportsfandom.databinding.ActivityTeamInfoBinding
import com.rysanek.sportsfandom.domain.usecases.LoadTeams
import com.rysanek.sportsfandom.domain.usecases.SearchTeams
import com.rysanek.sportsfandom.domain.utils.fullScreenMode
import com.rysanek.sportsfandom.domain.utils.gone
import dagger.hilt.android.AndroidEntryPoint
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class TeamInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamInfoBinding
    @Inject lateinit var teams: LoadTeams
    @Inject lateinit var search: SearchTeams
    private val args by navArgs<TeamInfoActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {

        fullScreenMode()

        super.onCreate(savedInstanceState)

        binding = ActivityTeamInfoBinding.inflate(layoutInflater)

        binding.adView.loadAd(WeakReference(AdRequest.Builder().build()).get()!!)

        loadData()

        setContentView(binding.root)
    }

    private fun loadData(){
        with(args.teamEntity){
            with(binding){

                loadImage(strTeamBadge, binding.ivTeamBadge)
                tvTitle.text = strTeam ?: ""
                tvFormedInfo.text = intFormedYear ?: ""
                tvCountryInfo.text = strCountry ?: ""
                tvSportTypeInfo.text = strSport ?: ""
                tvDescriptionInfo.text = strDescriptionEN ?: ""

                val leagues = listOf(strLeague, strLeague2, strLeague3, strLeague4, strLeague5, strLeague6, strLeague7)

                binding.tvLeaguesInfo.text = returnLeaguesStr(leagues)

                loadImage(strTeamJersey, ivTeamJersey)
                loadImage(strTeamLogo, ivTeamLogo)
                loadImage(strTeamFanart1, ivTeamArt1)
                loadImage(strTeamFanart2, ivTeamArt2)
                loadImage(strTeamFanart2, ivTeamArt3)
                loadImage(strTeamFanart4, ivTeamArt4)
                loadImage(strTeamBanner, ivTeamBanner)
            }
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}