package com.example.jetcasterme.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetcasterme.model.Audio

@Database(entities = [Audio::class], version = 1)
abstract class JetcasterDatabase : RoomDatabase(){
    abstract fun audioDao():AudioDao
}