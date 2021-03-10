package com.negahpay.sokkan.framework.di

import com.negahpay.sokkan.BuildConfig
import com.negahpay.sokkan.framework.net.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideMoshi() =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideNetParams() = NetParams()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient, moshi: Moshi, netParams: NetParams
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(netParams.baseUrl)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun providesTokenInterceptor(netParams: NetParams): TokenInterceptor =
        TokenInterceptor(netParams, netParams.token)

    @Provides
    @Singleton
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(tokenInterceptor)

        if (BuildConfig.DEBUG)
            httpClient.addInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    @Provides
    fun provideUserService(retrofit: Retrofit): IUserService =
        retrofit.create(IUserService::class.java)

    @Provides
    fun provideTokenService(retrofit: Retrofit): ITokenService =
        retrofit.create(ITokenService::class.java)
}