package com.rysanek.sportsfandom.ui.viewmodels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.data.repositories.scores.ScoresRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val repository: ScoresRepositoryImpl
) : ViewModel() {

    private var timerTask: TimerTask? = null
    private var timer: Timer? = null

    fun getScores() = repository.getScoresFromDbLiveData()

    fun fetchScores() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TeamsViewModel", "Fetch Started")
        repository.fetchScores()
            .catch { e -> Log.e("TeamsViewmodel", "Error Downloading: ${e.message}") }
            .map { scoresList ->

                if(!scoresList.events.isNullOrEmpty()) {
                    val scoresValue = repository.getScoresFromDb().size

                    if (scoresValue <= 0) {
                        repository.deleteAllScoresFromDb()
                        repository.insertScoresToDb( scoresList.events.map { dto -> dto.toScoreEntity() })
                    } else {
                        Log.d("ScoresViewModel", "updating score values")
                        repository.updateScores(scoresList.events.map { dto -> dto.toScoreEntity() })
                    }
                }

                startReFetchTask()

                Log.d("ViewModel", "Response: ${scoresList.events}")
            }
            .collect()
    }

    fun loadImagesToView(url: String, imageView: ImageView) = viewModelScope.launch(Dispatchers.Main) {
        repository.loadImagesToView(url, imageView)
    }

    private fun startReFetchTask(): Job = viewModelScope.launch(Dispatchers.IO) {
        timerTask = object: TimerTask(){
            override fun run() {
                fetchScores()
            }
        }

        timer = Timer()
        timer?.schedule(timerTask, 120000)
    }

    fun stopTimerTask(){
        timerTask?.cancel()
        timerTask = null
        timer = null

        Log.d("ScoresViewModel", "cancelled: Timer Task - $timerTask, Timer - $timer ")
    }

}