package com.flaquir4.codetest.domain.errors

sealed class AuthenticationError : RuntimeException() {
    object BadCredentials : AuthenticationError()
    object NetworkError : AuthenticationError()
    object DiskError : AuthenticationError()
}
