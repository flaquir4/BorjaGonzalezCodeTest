package com.flaquir4.codetest.presentation.login.validator

import android.util.Patterns
import cat.helm.result.Result
import cat.helm.result.asFailure
import cat.helm.result.asSuccess

interface FormValidator<Error : Exception> {

    fun String.isAnEmail(): Result<Unit, LoginError> =
        if (Patterns.EMAIL_ADDRESS.matcher(this).matches()) {
            Unit.asSuccess()
        } else {
            LoginError.WrongEmailFormat.asFailure()
        }

    fun String.emailIsNotEmpty(): Result<Unit, LoginError> =
        isntEmpty().mapError { LoginError.EmptyEmail }

    fun String.passwordIsNotEmpty(): Result<Unit, LoginError> =
        isntEmpty().mapError { LoginError.EmptyPassword }

    fun String.hasEnoughLength(requiredLength: Int = 8): Result<Unit, LoginError> =
        if (length >= requiredLength) {
            Unit.asSuccess()
        } else {
            LoginError.ShortPassword.asFailure()
        }

    fun validate(
        vararg resultSequence: Result<Unit, LoginError>,
        onError: (List<Error>) -> Unit
    ) {
        val failures = resultSequence.filterIsInstance<Result.Failure<Error>>().map { it.exception }
        if (failures.isNotEmpty()) {
            onError(failures)
        }
    }

    private fun String.isntEmpty(): Result<Unit, kotlin.Error> =
        if (isNotEmpty()) {
            Unit.asSuccess()
        } else {
            kotlin.Error().asFailure()
        }
}
