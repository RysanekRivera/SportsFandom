package com.rysanek.sportsfandom.ui.activities

import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.databinding.ActivityVideoPlayerBinding
import com.rysanek.sportsfandom.domain.utils.MetaData
import com.rysanek.sportsfandom.domain.utils.fullScreenMode

class VideoPlayer: YouTubeBaseActivity(){

    private lateinit var binding: ActivityVideoPlayerBinding
    private var listener: YouTubePlayer.OnInitializedListener? = null
    private var rewardedAd: RewardedAd? = null
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreenMode()

        super.onCreate(savedInstanceState)

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)

        window.decorView.setBackgroundColor(resources.getColor(R.color.black, theme))

        val videoId = intent.getStringExtra("videoId")

        listener = object: YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                player: YouTubePlayer?,
                p2: Boolean
            ) {
                player?.loadVideo(videoId)

                player?.fullscreenControlFlags
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("VideoPlayer", "result: $p1")
            }
        }

        binding.youTubePlayerView.initialize(MetaData.YT, listener)

        setContentView(binding.root)
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
                this@VideoPlayer.rewardedAd = rewardedAd
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


    override fun onDestroy() {
        super.onDestroy()
        listener = null
        binding.root.removeAllViews()
    }

}