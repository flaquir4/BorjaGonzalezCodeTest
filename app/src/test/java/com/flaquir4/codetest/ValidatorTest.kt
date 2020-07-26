package com.flaquir4.codetest

import cat.helm.result.Result
import com.flaquir4.codetest.presentation.login.validator.FormValidator
import com.flaquir4.codetest.presentation.login.validator.LoginError
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ValidatorTest : FormValidator<LoginError> {

    companion object {
        const val VALID_EMAIL = "flaquir4@gmail.com"
        const val INVALID_EMAIL = "not an email@.com"
        const val INVALID_PASSWORD = "1234567"
        const val EMPTY_TEXT_FIELD = ""
        const val VALID_PASSWORD = "12345678"
        const val ANOTHER_VALID_PASSWORD = "12345678"
    }


    @Test
    fun `assert is email returns success on valid email`() {
        assertTrue { VALID_EMAIL.isAnEmail().isSuccess() }
    }

    @Test
    fun `assert is email returns failure on wrong email`() {
        assertTrue { (INVALID_EMAIL.isAnEmail() as Result.Failure).exception is LoginError.WrongEmailFormat }
    }

    @Test
    fun `assert email is not empty return success when is not empty`() {
        assertTrue(INVALID_EMAIL.emailIsNotEmpty().isSuccess())
    }

    @Test
    fun `assert email is not empty returns empty email`() {
        assertTrue((EMPTY_TEXT_FIELD.emailIsNotEmpty() as Result.Failure).exception is LoginError.EmptyEmail)
    }

    @Test
    fun `assert password is not empty returns success`() {
        assertTrue { INVALID_PASSWORD.passwordIsNotEmpty().isSuccess() }
    }

    @Test
    fun `assert password is not return password is empty `() {
        assertTrue { (EMPTY_TEXT_FIELD.passwordIsNotEmpty() as Result.Failure).exception is LoginError.EmptyPassword }
    }

    @Test
    fun `assert password has enough length returns success on passwords bigger or equal than 8`() {
        assertTrue { VALID_PASSWORD.hasEnoughLength().isSuccess() }
        assertTrue { ANOTHER_VALID_PASSWORD.hasEnoughLength().isSuccess() }
    }

    @Test
    fun `assert password has enough lenth returns short password on password smaller than 8`() {
        assertTrue { (INVALID_PASSWORD.hasEnoughLength() as Result.Failure).exception is LoginError.ShortPassword }
    }

    @Test
    fun `assert validate returns correct errors`() {
        validate(
            EMPTY_TEXT_FIELD.emailIsNotEmpty(),
            EMPTY_TEXT_FIELD.passwordIsNotEmpty(),
            EMPTY_TEXT_FIELD.isAnEmail(),
            EMPTY_TEXT_FIELD.hasEnoughLength()
        ) {
            assertEquals(
                listOf(
                    LoginError.EmptyEmail,
                    LoginError.EmptyPassword,
                    LoginError.WrongEmailFormat,
                    LoginError.ShortPassword
                ), it
            )
        }

    }

    @Test
    fun `assert validate returns some errors and some success`() {
        validate(
            INVALID_EMAIL.emailIsNotEmpty(),
            INVALID_PASSWORD.passwordIsNotEmpty(),
            INVALID_EMAIL.isAnEmail(),
            INVALID_PASSWORD.hasEnoughLength()
        ) {
            assertEquals(
                listOf(
                    LoginError.WrongEmailFormat,
                    LoginError.ShortPassword
                ), it
            )
        }
    }

    @Test
    fun `assert validate does not call onError if there are no failures`() {
        validate(
            VALID_EMAIL.emailIsNotEmpty(),
            VALID_PASSWORD.passwordIsNotEmpty(),
            VALID_EMAIL.isAnEmail(),
            VALID_PASSWORD.hasEnoughLength()
        ) {
            assertTrue(false)
        }

        assertTrue(true)
    }
}