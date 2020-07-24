package com.flaquir4.codetest.data

import cat.helm.result.Result
import com.flaquir4.codetest.domain.errors.AuthenticationError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class AuthenticationFakeApiDataSource @Inject constructor() : AuthenticationApiDataSource {

    override suspend fun login(
        username: String,
        password: String
    ): Result<String, AuthenticationError> =
        withContext(Dispatchers.IO) {
            Result.of {
                simulateNetworkDelay()
                JWT_TOKEN
            }.mapError {
                AuthenticationError.BadCredentials
            }
        }

    private suspend fun simulateNetworkDelay() {
        delay(Random.nextLong(MINIMUM_DELAY_TIME, MAXIMUM_DELAY_TIME))
    }

    companion object {
        const val MINIMUM_DELAY_TIME = 500L
        const val MAXIMUM_DELAY_TIME = 2000L
        const val JWT_TOKEN = """
                eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
                eyJhdWRpZW5jZSI6IkthcnVtaSIsIm5hbWUiOiJCb3JqYSBHb256w6FsZXoiLCJpYXQiOjE1MTYyMzkwMjJ9.
                euZaBQsTapD3-zrNEjER27sIfoyRp0qz98uYZntQHeA
            """
    }
}
