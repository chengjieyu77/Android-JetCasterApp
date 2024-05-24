package com.example.jetcasterme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetcasterme.model.Album

import com.example.jetcasterme.model.Audio
import com.example.jetcasterme.model.CollectionName


@Database(entities = [Audio::class, Album::class, CollectionName::class],
    version = 3, exportSchema = false)
abstract class JetcasterDatabase : RoomDatabase(){
    abstract fun jetcasterDao():JetcasterDatabaseDao
//    abstract fun audioDao():AudioDao
//    abstract fun albumDao():AlbumDao
//    abstract fun collectionNameDao():CollectionNameDao

}