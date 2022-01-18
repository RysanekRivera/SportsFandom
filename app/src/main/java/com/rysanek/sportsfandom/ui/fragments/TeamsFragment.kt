package com.rysanek.sportsfandom.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.databinding.FragmentTeamsBinding
import com.rysanek.sportsfandom.domain.utils.Constants.TEAMS_SCROLL_POSITION
import com.rysanek.sportsfandom.domain.utils.gone
import com.rysanek.sportsfandom.domain.utils.show
import com.rysanek.sportsfandom.ui.adapters.TeamsAdapter
import com.rysanek.sportsfandom.ui.viewmodels.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment: Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var rvAdapter: TeamsAdapter
    private val scrollPosition: SharedPreferences by lazy { requireContext().getSharedPreferences(TEAMS_SCROLL_POSITION, MODE_PRIVATE) }
    private val viewModel: TeamsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.getTeamsInfo().observe(viewLifecycleOwner){ teamsList -> handleListChanges(teamsList) }

        return binding.root
    }

    private fun handleListChanges(teamsList: List<TeamEntity>?){
        val position = scrollPosition.getInt(TEAMS_SCROLL_POSITION, 0)

        val list = teamsList ?: mutableListOf()

        rvAdapter.setData(list as MutableList)

        if (position > 0 && binding.rvTeams.scrollY >= position) binding.rvTeams.scrollTo(0, position)

        if (teamsList.isNullOrEmpty()) binding.tvTeamsAddInfo.show()
        else binding.tvTeamsAddInfo.gone()
    }

    private fun setupRecyclerView(){
        rvAdapter = TeamsAdapter(viewModel)

        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvTeams.apply {
            adapter = rvAdapter

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }

    override fun onPause() {
        super.onPause()
        val yPosition = binding.rvTeams.scrollY
        scrollPosition.edit { putInt(TEAMS_SCROLL_POSITION, yPosition) }
    }
    
}