package com.negahpay.sokkan.framework.di

import android.content.Context
import com.negahpay.sokkan.BuildConfig
import com.negahpay.sokkan.framework.db.DatabaseService
import com.negahpay.sokkan.framework.db.SettingsDao
import com.negahpay.sokkan.framework.net.SettingsRemoteDataSource
import com.negahpay.sokkan.framework.net.SettingsService
import com.negahpay.sokkan.framework.repo.SettingsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: String) =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    @Provides
    fun provideMoshi() =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideSettingsService(retrofit: Retrofit) =
        retrofit.create(SettingsService::class.java)

    @Provides
    @Singleton
    fun provideSettingsRemoteDataSource(settingsService: SettingsService) =
        SettingsRemoteDataSource(settingsService)

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
    fun provideSettingsRepository(
        remote: SettingsRemoteDataSource,
        local: SettingsDao
    ) =
        SettingsRepository(remote, local)
}