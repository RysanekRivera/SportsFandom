package com.rysanek.sportsfandom.ui.activities

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.youtube.player.YouTubeBaseActivity
import com.rysanek.sportsfandom.data.remote.apis.VideosAPI
import com.rysanek.sportsfandom.data.repositories.videos.VideosRepositoryImpl
import com.rysanek.sportsfandom.databinding.ActivityVideoHighlightsBinding
import com.rysanek.sportsfandom.domain.usecases.GetVideoUrls
import com.rysanek.sportsfandom.domain.utils.Constants.BASE_URL
import com.rysanek.sportsfandom.domain.utils.fullScreenMode
import com.rysanek.sportsfandom.ui.adapters.VideosAdapter
import com.rysanek.sportsfandom.ui.viewmodels.VideosViewModel
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VideoHighlights: YouTubeBaseActivity(){

    private lateinit var binding: ActivityVideoHighlightsBinding
    private var rvAdapter: VideosAdapter? = null
    private var api: VideosAPI? = null
    private var repository: VideosRepositoryImpl? = null
    private var getVideoUrls: GetVideoUrls? = null
    private var viewModel: VideosViewModel? = null
    private var rewardedAd: RewardedAd? = null
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreenMode()

        super.onCreate(savedInstanceState)

        setupRewardedAds()

        binding = ActivityVideoHighlightsBinding.inflate(layoutInflater)

        setupRecyclerView()

        setupDependencies()

        viewModel!!.viewModelScope.launch {
            val videos = viewModel!!.getVideoUrls()
            if (!videos.isNullOrEmpty()) rvAdapter!!.setData(videos as MutableList)
        }

        setContentView(binding.root)
    }

    private fun setupDependencies(){
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient().newBuilder()
                .addNetworkInterceptor(HttpLoggingInterceptor().also { interceptor -> interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC) }).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideosAPI::class.java)

        repository = VideosRepositoryImpl(api!!)

        getVideoUrls = GetVideoUrls(repository!!)

        viewModel = VideosViewModel(getVideoUrls!! )

    }

    private fun setupRecyclerView(){
        rvAdapter = VideosAdapter()

        rvAdapter!!.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvVideos.apply {
            adapter = rvAdapter

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

            layoutManager = GridLayoutManager(this@VideoHighlights, spanCount)
        }
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
                this@VideoHighlights.rewardedAd = rewardedAd
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

    override fun onPause() {
        super.onPause()
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
        rewardedAd = null
        rvAdapter = null
        api = null
        repository = null
        getVideoUrls = null
        viewModel= null
        binding.root.removeAllViews()
    }
}