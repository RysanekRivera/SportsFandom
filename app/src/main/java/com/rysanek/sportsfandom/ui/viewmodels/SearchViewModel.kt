package com.rysanek.sportsfandom.ui.viewmodels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.data.repositories.search.SearchRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepositoryImpl
) : ViewModel() {

    fun getTeamsInfo() = repository.getTeamsInfoFromDbLiveData()

    private var searchJob: Job? = null

    fun fetchTeams(teamName: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("TeamsViewModel", "Fetch Started")

        if (teamName.length > 1){
            searchJob?.cancel()
            searchJob = viewModelScope.launch {

                delay(500)
                repository.fetchTeams(teamName)
                    .catch { e -> Log.e("TeamsViewmodel", "Error Downloading: ${e.message}") }
                    .map { teamsList ->

                        if(!teamsList.searchResults.isNullOrEmpty()) {
                            repository.deleteAllTeamsFromDb()
                            repository.insertTeamToDb( teamsList.searchResults.map { dto -> dto.toTeamEntity() })
                        }

                        Log.d("ViewModel", "Response: ${teamsList.searchResults}")
                    }
                    .collect()

                repository.getTeamsInfoFromDbLiveData()
            }
        } else {
            repository.deleteAllTeamsFromDb()
        }

    }

    fun loadImagesToView(url: String, imageView: ImageView) = viewModelScope.launch(Dispatchers.Main) {
        repository.loadImagesToView(url, imageView)
    }

}