package com.example.networkkit.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.networkkit.data.api.ApiService
import com.example.networkkit.data.api.model.LoginRequest
import com.example.networkkit.data.api.model.LoginResponse
import com.example.networkkit.data.api.model.ProfileResponse
import com.example.networkkit.domain.NetworkException
import com.example.networkkit.domain.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) {

    // Универсальный метод-обёртка для любого API-вызова
    private suspend fun <T> safeApiCall(call: suspend () -> T): NetworkResult<T> = withContext(Dispatchers.IO) {
        try {
            if (!isNetworkAvailable(context)) {
                return@withContext NetworkResult.Error(NetworkException.NoInternet())
            }

            val result = call()
            NetworkResult.Success(result)
        } catch (e: HttpException) {
            val error = when (e.code()) {
                401 -> NetworkException.Unauthorized()
                else -> NetworkException.Server(e.code(), e.message() ?: "Ошибка сервера")
            }
            NetworkResult.Error(error)
        } catch (e: IOException) {
            NetworkResult.Error(NetworkException.NoInternet())
        } catch (e: Exception) {
            NetworkResult.Error(NetworkException.Unknown(cause = e))
        }
    }

    // Пример: метод логина (возвращает Flow)
    fun login(email: String, password: String): Flow<NetworkResult<LoginResponse>> = flow {
        emit(NetworkResult.Loading)

        val result = safeApiCall {
            apiService.login(LoginRequest(email, password))
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
        .catch { throwable ->
            emit(NetworkResult.Error(NetworkException.Unknown(cause = throwable)))
        }

    // Пример: профиль
    fun getProfile(): Flow<NetworkResult<ProfileResponse>> = flow {
        emit(NetworkResult.Loading)

        val result = safeApiCall { apiService.getProfile() }
        emit(result)
    }.flowOn(Dispatchers.IO)
        .catch { throwable ->
            emit(NetworkResult.Error(NetworkException.Unknown(cause = throwable)))
        }

    // ... добавь остальные методы аналогично (register, getCatalog и т.д.)

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}