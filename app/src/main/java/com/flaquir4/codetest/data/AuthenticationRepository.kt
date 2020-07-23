package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationErrors

interface AuthenticationRepository {

    suspend fun login(username: String, password: String): Result<Unit, AuthenticationErrors>
}
