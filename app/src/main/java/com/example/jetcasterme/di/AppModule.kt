package com.example.jetcasterme.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)//one source of truth
@Module
object AppModule {
}