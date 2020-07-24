package com.flaquir4.codetest.domain

import cat.helm.result.Result
import com.flaquir4.codetest.data.AuthenticationRepository
import com.flaquir4.codetest.domain.errors.AuthenticationError
import javax.inject.Inject

class IsUserLoggedIn @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend operator fun invoke(): Result<Boolean, AuthenticationError> =
        authenticationRepository.isUserLoggedIn()
}
