package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationError

interface AuthenticationApiDataSource {

    suspend fun login(username: String, password: String): Result<String, AuthenticationError>
}
