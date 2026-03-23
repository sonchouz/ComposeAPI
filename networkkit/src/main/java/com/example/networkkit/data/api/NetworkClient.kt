package com.example.networkkit.data.api
import android.content.Context
import com.example.networkkit.data.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

object NetworkClient {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    fun create(baseUrl: String, context: Context): ApiService {
        // Логгер запросов (очень полезно на этапе разработки)
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // или .HEADERS для меньшего лога
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)                    // логирование всех запросов
            .addInterceptor(NetworkInterceptor(context))           // твой интерсептор (токен, ошибки и т.д.)
            .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
    }
}