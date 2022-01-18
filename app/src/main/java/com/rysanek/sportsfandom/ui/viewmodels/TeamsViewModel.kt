package com.rysanek.sportsfandom.ui.viewmodels

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.data.repositories.teams.TeamsRepositoryImpl
import com.rysanek.sportsfandom.domain.usecases.LoadTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val loadTeams: LoadTeams
) : ViewModel() {

    fun getTeamsInfo() = loadTeams.getTeamsInfo()

    fun loadImagesToView(url: String, imageView: ImageView) = viewModelScope.launch(Dispatchers.Main) {
        loadTeams.loadImagesToView(url, imageView)
    }

    fun saveTeamToDb(team: TeamEntity) = viewModelScope.launch(Dispatchers.IO) {
        loadTeams.saveTeamToDb(team)
    }

    fun deleteTeamFromDb(team: TeamEntity) = viewModelScope.launch(Dispatchers.IO) {
        loadTeams.deleteTeamFromDb(team)
    }

}