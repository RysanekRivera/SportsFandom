package com.rysanek.sportsfandom.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.databinding.SingleSearchLayoutBinding
import com.rysanek.sportsfandom.domain.utils.ListsDiffUtil
import com.rysanek.sportsfandom.ui.viewmodels.SearchViewModel
import com.rysanek.sportsfandom.ui.viewmodels.TeamsViewModel

class SearchAdapter(
    private val viewModel: SearchViewModel,
    private val teamsViewModel: TeamsViewModel
) : RecyclerView.Adapter<SearchAdapter.TeamsViewHolder>() {

    private var currentTeamsList = mutableListOf<SearchEntity>()

    class TeamsViewHolder(private val binding: SingleSearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup): TeamsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleSearchLayoutBinding.inflate(layoutInflater, parent, false)

                return TeamsViewHolder(binding)
            }
        }

        fun bind(team: SearchEntity, viewModel: SearchViewModel) {
            team.strTeamBadge?.let { imageUrl -> viewModel.loadImagesToView(imageUrl, binding.ivTeamPic) }
            Log.d("Image", "ImageUrl: ${team.strTeamBadge}")
            binding.tvTeamName.text = team.strTeam

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = currentTeamsList[position]
        holder.bind(team, viewModel)
        holder.itemView.setOnLongClickListener {
            teamsViewModel.saveTeamToDb(team.toTeamEntity())
            Snackbar.make(it, "Saved: ${team.strTeam}", Snackbar.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount() = currentTeamsList.size

    fun setData(list: MutableList<SearchEntity>) {
        ListsDiffUtil(currentTeamsList, list).calculateDiff(this)
        currentTeamsList.clear()
        currentTeamsList = list
    }
}