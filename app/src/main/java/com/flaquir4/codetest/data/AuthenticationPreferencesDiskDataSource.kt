package com.flaquir4.codetest.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationError
import javax.inject.Inject

class AuthenticationPreferencesDiskDataSource @Inject constructor(private val context: Application) :
    AuthenticationDiskDataSource {
    override suspend fun saveToken(token: String): Result<Unit, AuthenticationError> = Result.of {
        val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        preferences.edit {
            putString(TOKEN_KEY, token)
        }
    }.mapError {
        AuthenticationError.BadCredentials
    }

    companion object {
        const val PREFERENCES_NAME = "CodeTest"
        const val TOKEN_KEY = "token"
    }
}
