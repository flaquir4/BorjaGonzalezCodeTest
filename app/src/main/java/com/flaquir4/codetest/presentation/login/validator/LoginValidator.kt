package com.flaquir4.codetest.presentation.login.validator

import javax.inject.Inject

class LoginValidator<Error : Exception> @Inject constructor() : FormValidator<Error> {

    fun validate(username: String, password: String, onError: (List<Error>) -> Unit) =
        validate(
            username.emailIsNotEmpty(),
            username.isAnEmail(),
            password.passwordIsNotEmpty(),
            password.hasEnoughLength()
        ) {
            onError(it)
        }
}
