package com.rysanek.sportsfandom.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rysanek.sportsfandom.data.local.entities.ScoresEntity
import com.rysanek.sportsfandom.databinding.SingleScoreLayoutBinding
import com.rysanek.sportsfandom.domain.utils.ListsDiffUtil
import com.rysanek.sportsfandom.domain.utils.gone
import com.rysanek.sportsfandom.domain.utils.show
import com.rysanek.sportsfandom.ui.viewmodels.ScoresViewModel

class ScoresAdapter(
    private val viewModel: ScoresViewModel
) : RecyclerView.Adapter<ScoresAdapter.TeamsViewHolder>() {

    private var currentTeamsList = mutableListOf<ScoresEntity>()

    class TeamsViewHolder(private val binding: SingleScoreLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup): TeamsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleScoreLayoutBinding.inflate(layoutInflater, parent, false)

                return TeamsViewHolder(binding)
            }
        }

        fun bind(scores: ScoresEntity, viewModel: ScoresViewModel) {

            scores.strHomeTeamBadge?.let { imageUrl -> viewModel.loadImagesToView(imageUrl, binding.ivHomeTeamPic) }
            scores.strAwayTeamBadge?.let { imageUrl -> viewModel.loadImagesToView(imageUrl, binding.ivAwayTeamPic) }

            binding.tvHomeTeamName.text = scores.strHomeTeam ?: ""
            binding.tvAwayTeamName.text = scores.strAwayTeam ?: ""

            binding.tvHomeTeamScore.text = scores.intHomeScore ?: ""
            binding.tvAwayTeamScore.text = scores.intAwayScore ?: ""

            val status = scores.strStatus
            val progress = scores.strProgress

            binding.tvProgress.text = progress
            binding.tvStatus.text = status
            binding.tvDate.text = scores.dateEvent
            binding.tvTime.text = scores.strEventTime

            if (status.equals("NS"))binding.tvStatus.gone()
            else binding.tvStatus.show()

            if (progress.equals("Final") || status.equals("FT") || status.equals("AOT") || status.equals("AP")) {
                when {
                    scores.intHomeScore?.toInt() ?: 0 > scores.intAwayScore?.toInt() ?: 0 -> {
                        binding.ivArrowHomeTeam.show()
                        binding.ivArrowAwayTeam.gone()
                    }
                    scores.intHomeScore?.toInt() ?: 0 < scores.intAwayScore?.toInt() ?: 0 -> {
                        binding.ivArrowAwayTeam.show()
                        binding.ivArrowHomeTeam.gone()
                    }
                    else -> { /*NO-OP*/ }
                }
            } else {
                binding.ivArrowHomeTeam.gone()
                binding.ivArrowAwayTeam.gone()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = currentTeamsList[position]
        holder.bind(team, viewModel)
    }

    override fun getItemCount() = currentTeamsList.size

    fun setData(list: MutableList<ScoresEntity>) {
        ListsDiffUtil(currentTeamsList, list).calculateDiff(this)
        currentTeamsList.clear()
        currentTeamsList = list
    }
}