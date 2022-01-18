package com.rysanek.sportsfandom.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.sportsfandom.R
import com.rysanek.sportsfandom.databinding.FragmentScoresBinding
import com.rysanek.sportsfandom.databinding.SingleScoreLayoutBinding
import com.rysanek.sportsfandom.domain.utils.*
import com.rysanek.sportsfandom.domain.utils.Constants.SCORE_SCROLL_POSITION
import com.rysanek.sportsfandom.ui.adapters.ScoresAdapter
import com.rysanek.sportsfandom.ui.adapters.SearchAdapter
import com.rysanek.sportsfandom.ui.viewmodels.ScoresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment: Fragment() {

    private lateinit var binding: FragmentScoresBinding
    private lateinit var rvAdapter: ScoresAdapter
    private val scrollPosition: SharedPreferences by lazy { requireContext().getSharedPreferences(SCORE_SCROLL_POSITION, MODE_PRIVATE) }
    private val viewModel: ScoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentScoresBinding.inflate(inflater, container, false)

        fetchScores()

        setupRecyclerView()

        viewModel.getScores().observe(viewLifecycleOwner){ scores ->
            val position = scrollPosition.getInt(SCORE_SCROLL_POSITION, 0)

            rvAdapter.setData(scores as MutableList)

            if (position > 0 && binding.rvScores.scrollY >= position) binding.rvScores.scrollTo(0, position)

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.downloadState.observe(viewLifecycleOwner){ state -> handleDownloadStates(state) }
    }

    override fun onPause() {
        super.onPause()
        val yPosition = binding.rvScores.scrollY
        scrollPosition.edit { putInt(SCORE_SCROLL_POSITION, yPosition) }
    }

    private fun setupRecyclerView(){
        rvAdapter = ScoresAdapter(viewModel)

        rvAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvScores.apply {
            adapter = rvAdapter

            val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 1 else 2

            layoutManager = GridLayoutManager(requireContext(), spanCount)
        }
    }

    private fun fetchScores(){
        viewModel.postDownloadState(DownloadState.Checking)
        if (hasInternetConnection()) viewModel.fetchScores()
    }

    private fun handleDownloadStates(state: DownloadState) {
        when(state){
            is DownloadState.Idle -> binding.pbScores.gone()
            is DownloadState.Downloading -> binding.pbScores.show()
            is DownloadState.Finished -> viewModel.postDownloadState(DownloadState.Idle)
            is DownloadState.Checking -> if (!hasInternetConnection()) {
                binding.pbScores.gone()
                showSnackbar(resources.getString(R.string.no_internet))
            }
            is DownloadState.Error -> {
                binding.pbScores.gone()
                showSnackbar(state.message ?: "An Error Occurred")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopTimerTask()
    }

}