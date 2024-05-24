package com.example.jetcasterme.di

import android.content.Context
import androidx.room.Room
import com.example.jetcasterme.data.AlbumDao
import com.example.jetcasterme.data.AudioDao
import com.example.jetcasterme.data.CollectionNameDao
import com.example.jetcasterme.data.JetcasterDatabase
import com.example.jetcasterme.data.JetcasterDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)//one source of truth
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context):JetcasterDatabase
        = Room.databaseBuilder(
            context,
            JetcasterDatabase::class.java,
            "jetcaster_db"
        ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesJetcasterDao(jetcasterDatabase: JetcasterDatabase):JetcasterDatabaseDao
            = jetcasterDatabase.jetcasterDao()

//    @Singleton
//    @Provides
//    fun providesAudioDao(jetcasterDatabase: JetcasterDatabase):AudioDao
//            = jetcasterDatabase.audioDao()
//
//    @Singleton
//    @Provides
//    fun providesCollectionNameDao(jetcasterDatabase: JetcasterDatabase):CollectionNameDao
//            = jetcasterDatabase.collectionNameDao()
//
//    @Singleton
//    @Provides
//    fun providesAlbumDao(jetcasterDatabase: JetcasterDatabase): AlbumDao
//            = jetcasterDatabase.albumDao()


}