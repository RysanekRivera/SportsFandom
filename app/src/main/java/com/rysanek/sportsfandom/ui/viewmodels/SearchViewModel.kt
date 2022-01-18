package com.rysanek.sportsfandom.ui.viewmodels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.domain.usecases.SearchTeams
import com.rysanek.sportsfandom.domain.utils.DownloadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchTeams: SearchTeams
) : ViewModel() {

    fun getTeamsInfo() = searchTeams.getSearchesFromDbLiveData()

    private var searchJob: Job? = null

    fun fetchTeams(teamName: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TeamsViewModel", "Fetch Started")

        if (teamName.length > 1) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch(Dispatchers.IO) {

                delay(500)
                searchTeams.startFetch(teamName)
                    .catch { e -> Log.e("TeamsViewmodel", "Error Downloading: ${e.message}") }
                    .map { teamsList ->
                        if(!teamsList.searchResults.isNullOrEmpty())
                        teamsList.searchResults.map { dto -> dto.toTeamEntity() }
                        else emptyList()
                    }
                    .collect { teamsList ->
                        searchTeams.deleteAllTeams()
                        searchTeams.insertTeams(teamsList)
                    }
            }
        } else {
            searchTeams.deleteAllTeams()
        }
    }

    fun loadImagesToView(url: String, imageView: ImageView) =
        viewModelScope.launch(Dispatchers.Main) {
            searchTeams.loadImagesToView(url, imageView)
        }

}