package com.rysanek.sportsfandom.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.databinding.FragmentSearchBinding
import com.rysanek.sportsfandom.domain.utils.hideKeyboard
import com.rysanek.sportsfandom.ui.adapters.SearchAdapter
import com.rysanek.sportsfandom.ui.viewmodels.SearchViewModel
import com.rysanek.sportsfandom.ui.viewmodels.TeamsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels()
    private val teamsViewModel: TeamsViewModel by viewModels()
    private val searchView: SearchView by lazy { requireActivity().findViewById(R.id.searchView) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupRecyclerView()

        searchViewModel.getTeamsInfo().observe(viewLifecycleOwner) { teams ->
            rvAdapter.setData(teams as MutableList)
        }

        searchView.setOnQueryTextListener(provideOnQueryTextListener())

        return binding.root
    }

    private fun setupRecyclerView(){
        rvAdapter = SearchAdapter(searchViewModel, teamsViewModel)

        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvSearch.apply {
            adapter = rvAdapter

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }

    private fun provideOnQueryTextListener() = object: SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {

            hideKeyboard()

            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {

            if (query != null) searchViewModel.fetchTeams(query)

            return false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setQuery("", false)
        searchView.isIconified = true
    }
}