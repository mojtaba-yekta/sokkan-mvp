package com.negahpay.sokkan.framework.di

import android.app.Application
import com.negahpay.core.repository.SettingRepository
import com.negahpay.core.repository.UserRepository
import com.negahpay.sokkan.framework.RoomSettingDataSource
import com.negahpay.sokkan.framework.RoomUserDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    fun providesUserRepository(app: Application) =
        UserRepository(RoomUserDataSource(app))

    @Provides
    fun providesSettingRepository(app: Application) =
        SettingRepository(RoomSettingDataSource(app))
}