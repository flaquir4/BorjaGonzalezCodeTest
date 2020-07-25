package com.flaquir4.codetest.data

import cat.helm.result.Result
import cat.helm.result.asSuccess
import cat.helm.result.asFailure
import cat.helm.result.flatMap
import cat.helm.result.recover
import com.flaquir4.codetest.domain.errors.AuthenticationError
import javax.inject.Inject

class AuthenticationDataRepository @Inject constructor(
    private val authenticationApiDataSource: AuthenticationApiDataSource,
    private val authenticationDiskDataSource: AuthenticationDiskDataSource
) : AuthenticationRepository {

    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit, AuthenticationError> =
        authenticationApiDataSource.login(username, password)
            .flatMap { authenticationDiskDataSource.saveToken(it) }
            .recover {
                if (it is AuthenticationError.DiskError) {
                    Unit.asSuccess()
                } else {
                    (it as AuthenticationError).asFailure()
                }
            }

    override suspend fun isUserLoggedIn(): Result<Boolean, AuthenticationError> =
        authenticationDiskDataSource.getToken().map {
            it.isNotEmpty()
        }

    override suspend fun logout(): Result<Unit, AuthenticationError> =
        authenticationDiskDataSource.deleteToken()
}
