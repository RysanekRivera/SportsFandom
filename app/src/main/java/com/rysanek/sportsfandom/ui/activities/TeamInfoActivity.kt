package com.rysanek.sportsfandom.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.navigation.navArgs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.mediation.MediationBannerAd
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
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
    private var rewardedAd: RewardedAd? = null
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        setupRewardedAds()

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

    private fun setupRewardedAds(){
        val rewardedAdRequest = AdRequest.Builder().build()

        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", rewardedAdRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.message)
                rewardedAd = null
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                this@TeamInfoActivity.rewardedAd = rewardedAd
            }
        })

        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() { Log.d(TAG, "Ad was shown.") }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) { Log.d(TAG, "Ad failed to show.") }

            override fun onAdDismissedFullScreenContent() {

                Log.d(TAG, "Ad was dismissed.")
                rewardedAd = null
            }
        }
    }

    override fun onBackPressed() {
        if (rewardedAd != null) {
            rewardedAd?.show(this) {
                fun onUserEarnedReward(rewardItem: RewardItem) {
                    var rewardAmount = rewardItem.amount
                    var rewardType = rewardItem.type
                    Log.d(TAG, "User earned the reward.")
                }
            }
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
        finish()
    }

}