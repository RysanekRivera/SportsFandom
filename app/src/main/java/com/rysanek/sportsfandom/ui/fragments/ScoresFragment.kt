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
import com.rysanek.sportsfandom.domain.utils.Constants.SCORE_SCROLL_POSITION
import com.rysanek.sportsfandom.ui.adapters.ScoresAdapter
import com.rysanek.sportsfandom.ui.adapters.SearchAdapter
import com.rysanek.sportsfandom.ui.viewmodels.ScoresViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment: Fragment() {

    private lateinit var binding: FragmentScoresBinding
    private lateinit var rvAdapter: ScoresAdapter
    val scrollPosition: SharedPreferences by lazy { requireContext().getSharedPreferences(SCORE_SCROLL_POSITION, MODE_PRIVATE) }
    private val viewModel: ScoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentScoresBinding.inflate(inflater, container, false)

        viewModel.fetchScores()

        setupRecyclerView()

        viewModel.getScores().observe(viewLifecycleOwner){ scores ->
            val position = if (scrollPosition.getInt(SCORE_SCROLL_POSITION, 0) == 0) binding.rvScores.scrollY
            else scrollPosition.getInt(SCORE_SCROLL_POSITION, 0)

            val list = scores ?: mutableListOf()

            rvAdapter.setData(list as MutableList)

            if (position > 0 && position < scores.size) { binding.rvScores.scrollToPosition(position) }
        }

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopTimerTask()
    }

}