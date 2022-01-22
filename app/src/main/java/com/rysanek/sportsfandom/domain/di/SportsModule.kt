package com.rysanek.sportsfandom.domain.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.bumptech.glide.RequestManager
import com.rysanek.sportsfandom.data.local.db.scores.ScoresDAO
import com.rysanek.sportsfandom.data.local.db.scores.ScoresDatabase
import com.rysanek.sportsfandom.data.local.db.search.SearchDAO
import com.rysanek.sportsfandom.data.local.db.search.SearchDatabase
import com.rysanek.sportsfandom.data.local.db.teams.TeamsDAO
import com.rysanek.sportsfandom.data.local.db.teams.TeamsDatabase
import com.rysanek.sportsfandom.data.remote.apis.ScoresAPI
import com.rysanek.sportsfandom.data.remote.apis.SearchAPI
import com.rysanek.sportsfandom.data.remote.apis.VideosAPI
import com.rysanek.sportsfandom.domain.glide.GlideApp
import com.rysanek.sportsfandom.domain.utils.Constants.BASE_URL
import com.rysanek.sportsfandom.domain.utils.Constants.SCORES_DATABASE
import com.rysanek.sportsfandom.domain.utils.Constants.SCORE_BASE_URL
import com.rysanek.sportsfandom.domain.utils.Constants.SEARCH_DATABASE
import com.rysanek.sportsfandom.domain.utils.Constants.TEAMS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SportsModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(HttpLoggingInterceptor().also { interceptor ->
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        }).build()

    @Provides
    @Singleton
    fun provideSearchAPI(
        okHttpClient: OkHttpClient
    ): SearchAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(SearchAPI::class.java)

    @Provides
    @Singleton
    fun provideScoresAPI(
        okHttpClient: OkHttpClient
    ): ScoresAPI = Retrofit.Builder()
        .baseUrl(SCORE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(ScoresAPI::class.java)

    @Provides
    @Singleton
    fun provideVideosAPI(
        okHttpClient: OkHttpClient
    ): VideosAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(VideosAPI::class.java)

    @Provides
    @Singleton
    fun provideSearchDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        SearchDatabase::class.java,
        SEARCH_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideScoresDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        ScoresDatabase::class.java,
        SCORES_DATABASE
    ).build()

    @Provides
    @Singleton
    fun provideTeamsDatabase(
        application: Application
    ) = Room.databaseBuilder(
        application,
        TeamsDatabase::class.java,
        TEAMS_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideSearchDAO(
        db: ScoresDatabase
    ): ScoresDAO = db.scoresDAO

    @Singleton
    @Provides
    fun provideScoresDAO(
        db: SearchDatabase
    ): SearchDAO = db.searchDAO

    @Singleton
    @Provides
    fun provideTeamsDAO(
        db: TeamsDatabase
    ): TeamsDAO = db.teamsDAO

    @Singleton
    @Provides
    fun provideContext(
        @ApplicationContext context: Context
    ) = context

    @Singleton
    @Provides
    fun provideGlideInstance(context: Context): RequestManager = GlideApp.with(context)

}