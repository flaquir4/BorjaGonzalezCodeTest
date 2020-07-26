package com.flaquir4.codetest.presentation.login.validator

import cat.helm.result.Result
import cat.helm.result.asFailure
import cat.helm.result.asSuccess
import java.util.regex.Pattern

interface FormValidator<Error : Exception> {

    companion object {
        const val DEFAULT_MIN_PASSWORD_SIZE = 8
        val EMAIL_ADDRESS: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
    }

    fun String.isAnEmail(): Result<Unit, LoginError> =
        if (EMAIL_ADDRESS.matcher(this).matches()) {
            Unit.asSuccess()
        } else {
            LoginError.WrongEmailFormat.asFailure()
        }

    fun String.emailIsNotEmpty(): Result<Unit, LoginError> =
        isntEmpty().mapError { LoginError.EmptyEmail }

    fun String.passwordIsNotEmpty(): Result<Unit, LoginError> =
        isntEmpty().mapError { LoginError.EmptyPassword }

    fun String.hasEnoughLength(requiredLength: Int = DEFAULT_MIN_PASSWORD_SIZE): Result<Unit, LoginError> =
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
