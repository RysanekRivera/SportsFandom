package com.rysanek.sportsfandom.ui.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.rysanek.sportsfandom.data.remote.dtos.videos.VideosDTO
import com.rysanek.sportsfandom.databinding.SingleVideoLayoutBinding
import com.rysanek.sportsfandom.domain.utils.ListsDiffUtil
import com.rysanek.sportsfandom.domain.utils.MetaData
import com.rysanek.sportsfandom.ui.activities.VideoPlayer
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.ref.WeakReference

@ActivityScoped
class VideosAdapter: RecyclerView.Adapter<VideosAdapter.VideosViewHolder>() {

    private var currentTeamsList = mutableListOf<VideosDTO>()

    class VideosViewHolder(private val binding: SingleVideoLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {

            fun from(parent: ViewGroup): VideosViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = SingleVideoLayoutBinding.inflate(layoutInflater, parent, false)

                return VideosViewHolder(binding)
            }
        }

        fun bind(video: VideosDTO) {
            var loader: YouTubeThumbnailLoader? = null
            val videoId = video.strVideo.removePrefix("https://www.youtube.com/watch?v=")

            val listener = WeakReference(object: YouTubeThumbnailView.OnInitializedListener {

                override fun onInitializationSuccess(
                    p0: YouTubeThumbnailView?,
                    p1: YouTubeThumbnailLoader?
                ) {

                    p1?.setVideo(videoId)

                    binding.ytPlayerView.setOnClickListener {

                        val intent = Intent(WeakReference(it.context).get(), VideoPlayer::class.java).apply {
                            putExtra("videoId", videoId)
                        }
                        WeakReference(it.context).get()?.startActivity(intent)
                    }

                }

                override fun onInitializationFailure(
                    p0: YouTubeThumbnailView?,
                    p1: YouTubeInitializationResult?
                ) {
                    Log.d("YouTubePlayer", "result: $p1")
                }
            }).get()


            Log.d(this::class.java.simpleName, "id: $videoId")
            binding.tvEvent.text = video.strEvent
            binding.ytPlayerView.initialize(MetaData.YT, listener)

            loader?.release()
            loader = null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        return VideosViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        val video = currentTeamsList[position]
        holder.bind(video)
    }

    override fun getItemCount() = currentTeamsList.size

    fun setData(list: MutableList<VideosDTO>) {
        ListsDiffUtil(currentTeamsList, list).calculateDiff(this)
        currentTeamsList.clear()
        currentTeamsList = list
    }

}