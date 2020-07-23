package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationErrors
import javax.inject.Inject

class AuthenticationDataRepository @Inject constructor(
    private val authenticationApiDataSource: AuthenticationApiDataSource
) : AuthenticationRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit, AuthenticationErrors> =
        authenticationApiDataSource.login(username, password)
}
