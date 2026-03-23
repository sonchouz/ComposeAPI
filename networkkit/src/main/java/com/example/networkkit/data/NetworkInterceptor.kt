package com.example.networkkit.data

import android.content.Context
import com.example.networkkit.domain.NetworkException
import okhttp3.Response
import okhttp3.Interceptor

class NetworkInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()


        val token = getAuthToken(context)

        val requestBuilder = originalRequest.newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.header("Authorization", "Bearer $token")
        }


        requestBuilder.header("Accept", "application/json")

        val request = requestBuilder.build()

        val response = chain.proceed(request)


        if (response.code == 401) {

             throw NetworkException.Unauthorized()
        }

        return response
    }

    private fun getAuthToken(context: Context): String? {

        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        return prefs.getString("access_token", null)
    }
}