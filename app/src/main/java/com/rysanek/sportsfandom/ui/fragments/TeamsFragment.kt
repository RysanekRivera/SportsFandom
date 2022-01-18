package com.rysanek.sportsfandom.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.databinding.FragmentSearchBinding
import com.rysanek.sportsfandom.databinding.FragmentTeamsBinding
import com.rysanek.sportsfandom.domain.utils.gone
import com.rysanek.sportsfandom.domain.utils.show
import com.rysanek.sportsfandom.ui.adapters.SearchAdapter
import com.rysanek.sportsfandom.ui.adapters.TeamsAdapter
import com.rysanek.sportsfandom.ui.viewmodels.SearchViewModel
import com.rysanek.sportsfandom.ui.viewmodels.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamsFragment: Fragment() {

    private lateinit var binding: FragmentTeamsBinding
    private lateinit var rvAdapter: TeamsAdapter
    private val viewModel: TeamsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)

        setupRecyclerView()

        viewModel.getTeamsInfo().observe(viewLifecycleOwner){ teamsList ->
            rvAdapter.setData(teamsList as MutableList)
            if (teamsList.isNullOrEmpty()) binding.tvTeamsAddInfo.show()
            else binding.tvTeamsAddInfo.gone()
        }

        return binding.root
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
    
}