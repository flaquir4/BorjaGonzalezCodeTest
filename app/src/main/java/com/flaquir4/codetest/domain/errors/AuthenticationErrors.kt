package com.flaquir4.codetest.domain.errors

sealed class AuthenticationErrors : RuntimeException() {
    object BadCredentials : AuthenticationErrors()
    object NetworkError : AuthenticationErrors()
}
