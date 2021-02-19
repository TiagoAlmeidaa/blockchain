package com.tiago.blockchain.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tiago.blockchain.data.network.BlockChainService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val URL_KEY = "url.key"
    }

    @Provides
    @Named(URL_KEY)
    fun provideUrl(): String {
        return "https://api.blockchain.info/" // TODO Ajustar no BuildConfig
    }

    @Provides
    @Singleton
    fun provideBlockChainService(retrofit: Retrofit): BlockChainService =
        retrofit
            .create(BlockChainService::class.java)

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(@Named(URL_KEY) url: String, httpClient: OkHttpClient, gson: Gson): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(url)
            .client(httpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()
}