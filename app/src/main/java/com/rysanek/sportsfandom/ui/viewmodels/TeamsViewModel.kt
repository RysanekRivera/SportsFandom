package com.rysanek.sportsfandom.ui.viewmodels

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.data.repositories.teams.TeamsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val repository: TeamsRepositoryImpl
) : ViewModel() {

    fun getTeamsInfo() = repository.getTeamsInfoFromDbLiveData()

    fun loadImagesToView(url: String, imageView: ImageView) = viewModelScope.launch(Dispatchers.Main) {
        repository.loadImagesToView(url, imageView)
    }

    fun saveTeamToDb(team: TeamEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTeamToDb(team)
    }

    fun deleteTeamFromDb(team: TeamEntity) = viewModelScope.launch {
        repository.deleteTeamFromDb(team)
    }

}