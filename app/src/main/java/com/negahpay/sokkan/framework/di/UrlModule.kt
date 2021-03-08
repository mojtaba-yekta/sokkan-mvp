package com.negahpay.sokkan.framework.di

import com.negahpay.sokkan.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UrlModule {
    @Provides
    fun provideBaseUrl():String = BuildConfig.Base_URL
}