package com.negahpay.sokkan.framework.di

import com.negahpay.sokkan.framework.db.SettingsDao
import com.negahpay.sokkan.framework.db.UserDao
import com.negahpay.sokkan.framework.net.SettingsRemoteDataSource
import com.negahpay.sokkan.framework.repo.SettingsRepository
import com.negahpay.sokkan.framework.repo.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(
        remote: SettingsRemoteDataSource,
        local: SettingsDao
    ) =
        SettingsRepository(remote, local)

    @Provides
    @Singleton
    fun provideUserRepository(local: UserDao) = UserRepository(local)
}