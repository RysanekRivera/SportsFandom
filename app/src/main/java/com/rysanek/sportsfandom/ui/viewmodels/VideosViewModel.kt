package com.rysanek.sportsfandom.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.rysanek.sportsfandom.domain.usecases.GetVideoUrls

class VideosViewModel(
    private val getVideoUrls: GetVideoUrls
): ViewModel() {

    suspend fun getVideoUrls() = getVideoUrls.fetchVideoUrls().videos

}