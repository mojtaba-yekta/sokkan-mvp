package com.negahpay.sokkan.framework.di

import android.content.Context
import com.negahpay.sokkan.framework.db.DatabaseService
import com.negahpay.sokkan.framework.mem.SharedPrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        DatabaseService.getInstance(context)

    @Provides
    @Singleton
    fun provideUserDao(db: DatabaseService) =
        db.userDao()

    @Provides
    @Singleton
    fun provideSharedPref() = SharedPrefService()

}