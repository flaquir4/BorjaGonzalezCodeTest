package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationErrors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class AuthenticationFakeApiDataSource @Inject constructor() : AuthenticationApiDataSource {

    override suspend fun login(
        username: String,
        password: String
    ): Result<Unit, AuthenticationErrors> =
        withContext(Dispatchers.IO) {
            Result.of {
                simulateNetworkDelay()
            }.mapError {
                AuthenticationErrors.BadCredentials
            }
        }

    private suspend fun simulateNetworkDelay() {
        delay(Random.nextLong(MINIMUM_DELAY_TIME, MAXIMUM_DELAY_TIME))
    }

    companion object {
        const val MINIMUM_DELAY_TIME = 500L
        const val MAXIMUM_DELAY_TIME = 2000L
    }
}
