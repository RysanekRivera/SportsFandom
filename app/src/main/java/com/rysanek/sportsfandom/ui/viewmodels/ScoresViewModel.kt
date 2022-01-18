package com.rysanek.sportsfandom.ui.viewmodels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.domain.usecases.FetchScores
import com.rysanek.sportsfandom.domain.utils.DownloadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    private val fetchScores: FetchScores
) : ViewModel() {

    private val _downloadState = MutableLiveData<DownloadState>(DownloadState.Idle)
    val downloadState: LiveData<DownloadState> = _downloadState
    private var timerTask: TimerTask? = null
    private var timer: Timer? = null

    fun getScores() = fetchScores.getScoresLiveData()

    fun postDownloadState(state: DownloadState) { _downloadState.postValue(state) }

    fun fetchScores() = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TeamsViewModel", "Fetch Started")
        postDownloadState(DownloadState.Downloading)

        fetchScores.startFetch()
            .catch { e -> postDownloadState(DownloadState.Error.message(e.message)) }
            .map { scoresDTOList ->
                if (!scoresDTOList.scores.isNullOrEmpty())
                scoresDTOList.scores.map { dto -> dto.toScoreEntity() }
                else emptyList()
            }
            .onCompletion { postDownloadState(DownloadState.Finished) }
            .collect { scoresList ->
                if(!scoresList.isNullOrEmpty()) {
                    val scoresValue = fetchScores.getScoresListSize()

                    if (scoresValue <= 0) {
                        fetchScores.deleteAllScores()
                        fetchScores.insertScores(scoresList)
                    } else {
                        Log.d("ScoresViewModel", "updating score values")
                        fetchScores.updateScores(scoresList)
                    }
                }

                startReFetchTask()
            }
    }

    fun loadImagesToView(url: String, imageView: ImageView) = viewModelScope.launch(Dispatchers.Main) {
        fetchScores.loadImages(url, imageView)
    }

    private fun startReFetchTask(): Job = viewModelScope.launch(Dispatchers.IO) {
        timerTask = object: TimerTask(){ override fun run() { fetchScores() } }

        timer = Timer()
        timer?.schedule(timerTask, 120000)
    }

    fun stopTimerTask(){
        timerTask?.cancel()
        timerTask = null
        timer = null
    }

}