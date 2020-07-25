package com.flaquir4.codetest.presentation.login.validator

import java.lang.RuntimeException

sealed class LoginError : RuntimeException() {
    object EmptyPassword : LoginError()
    object EmptyEmail : LoginError()
    object WrongEmailFormat : LoginError()
    object ShortPassword : LoginError()
}
