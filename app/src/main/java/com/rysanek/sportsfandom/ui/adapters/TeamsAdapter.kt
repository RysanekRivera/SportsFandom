package com.rysanek.sportsfandom.ui.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rysanek.sportsfandom.data.local.entities.SearchEntity
import com.rysanek.sportsfandom.data.local.entities.TeamEntity
import com.rysanek.sportsfandom.databinding.SingleSearchLayoutBinding
import com.rysanek.sportsfandom.databinding.SingleTeamLayoutBinding
import com.rysanek.sportsfandom.domain.utils.ListsDiffUtil
import com.rysanek.sportsfandom.ui.activities.TeamInfoActivity
import com.rysanek.sportsfandom.ui.fragments.TeamsFragmentDirections
import com.rysanek.sportsfandom.ui.viewmodels.SearchViewModel
import com.rysanek.sportsfandom.ui.viewmodels.TeamsViewModel

class TeamsAdapter(
    private val viewModel: TeamsViewModel
) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    private var currentTeamsList = mutableListOf<TeamEntity>()

    class TeamsViewHolder(private val binding: SingleTeamLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup): TeamsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SingleTeamLayoutBinding.inflate(layoutInflater, parent, false)

                return TeamsViewHolder(binding)
            }
        }

        fun bind(team: TeamEntity, viewModel: TeamsViewModel) {
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
            viewModel.deleteTeamFromDb(team)
            Snackbar.make(it, "Deleted: ${team.strTeam}", Snackbar.LENGTH_SHORT).show()
            true
        }

        holder.itemView.setOnClickListener {
            val navActions = TeamsFragmentDirections.actionTeamsFragmentToTeamInfoActivity(team)
            it.findNavController().navigate(navActions)
        }
    }

    override fun getItemCount() = currentTeamsList.size

    fun setData(list: MutableList<TeamEntity>) {
        ListsDiffUtil(currentTeamsList, list).calculateDiff(this)
        currentTeamsList.clear()
        currentTeamsList = list
    }
}