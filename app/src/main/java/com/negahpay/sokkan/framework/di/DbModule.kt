package com.negahpay.sokkan.framework.di

import android.content.Context
import com.negahpay.sokkan.framework.db.DatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        DatabaseService.getInstance(context)

    @Provides
    @Singleton
    fun provideSettingsDao(db: DatabaseService) =
        db.settingDao()

    @Provides
    @Singleton
    fun provideUserDao(db: DatabaseService) =
        db.userDao()
}