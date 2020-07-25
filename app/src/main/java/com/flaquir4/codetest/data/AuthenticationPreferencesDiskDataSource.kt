package com.flaquir4.codetest.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthenticationPreferencesDiskDataSource @Inject constructor(private val context: Application) :
    AuthenticationDiskDataSource {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override suspend fun saveToken(token: String): Result<Unit, AuthenticationError> =
        withContext(Dispatchers.IO) {
            Result.of {
                preferences.edit(commit = true) {
                    putString(TOKEN_KEY, token)
                }
            }.mapError {
                AuthenticationError.BadCredentials
            }
        }

    override suspend fun getToken(): Result<String, AuthenticationError> =
        withContext(Dispatchers.IO) {
            Result.of {
                preferences.getString(TOKEN_KEY, "")
            }.mapError {
                AuthenticationError.DiskError
            }
        }

    companion object {
        const val PREFERENCES_NAME = "CodeTest"
        const val TOKEN_KEY = "token"
    }
}
