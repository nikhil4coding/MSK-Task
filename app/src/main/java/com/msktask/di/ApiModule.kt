package com.msktask.di

import android.content.Context
import com.msktask.data.JsonRepositoryImpl
import com.msktask.domain.JsonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideJsonRepository(@ApplicationContext context: Context): JsonRepository{
        return JsonRepositoryImpl(context)
    }
}