package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationError

interface AuthenticationDiskDataSource {

    suspend fun saveToken(token: String): Result<Unit, AuthenticationError>
    suspend fun getToken(): Result<String, AuthenticationError>
}
