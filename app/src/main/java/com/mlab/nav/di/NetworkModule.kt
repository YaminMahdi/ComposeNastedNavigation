package com.mlab.nav.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.mlab.nav.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.jackson.jackson
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor(context = context)
    }

    @Singleton
    @Provides
    fun provideKtorClient(
        chuckerInterceptor: ChuckerInterceptor,
    ): HttpClient {
        val okHttpEngine = OkHttp.create {
            addInterceptor(chuckerInterceptor)
        }
        return HttpClient(okHttpEngine) {
            install(Logging) {
                level = LogLevel.BODY
            }
            defaultRequest {
                url(Constants.BASE_URL)
                header(HttpHeaders.Authorization, Constants.TOKEN)
                header(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json
                )
            }
            install(ContentNegotiation) {
                jackson {
                    setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
                }
            }
        }
    }

}
