package com.flaquir4.codetest.repository

import cat.helm.result.asFailure
import cat.helm.result.asSuccess
import com.flaquir4.codetest.data.AuthenticationApiDataSource
import com.flaquir4.codetest.data.AuthenticationDataRepository
import com.flaquir4.codetest.data.AuthenticationDiskDataSource
import com.flaquir4.codetest.data.AuthenticationRepository
import com.flaquir4.codetest.domain.errors.AuthenticationError
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class AuthenticationDataRepositoryTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val diskDataSource: AuthenticationDiskDataSource = mockk()
    private val apiDataSource: AuthenticationApiDataSource = mockk()
    lateinit var repository: AuthenticationRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = AuthenticationDataRepository(apiDataSource, diskDataSource)
    }

    @Test
    fun `login should return success if both data source respond success`() = runBlockingTest {
        givenASuccessfulLoginAndASavedToken()

        val result = repository.login("", "")

        assertTrue(result.isSuccess())

    }

    @Test
    fun `login should return success if api returns success but login returns failure`() =
        runBlockingTest {
            givenASuccessfulLoginAndAFailureSavingToken()

            val result = repository.login("", "")

            assertTrue(result.isSuccess())
        }

    @Test
    fun `login should return error if api returns error`() = runBlockingTest {
        givenApiReturnsError()

        val result = repository.login("", "")

        assertFalse(result.isSuccess())
    }


    private fun givenASuccessfulLoginAndASavedToken() {
        coEvery { diskDataSource.saveToken(any()) }.coAnswers { Unit.asSuccess() }
        coEvery { apiDataSource.login(any(), any()) }.coAnswers { "".asSuccess() }
    }

    private fun givenASuccessfulLoginAndAFailureSavingToken() {

        coEvery { diskDataSource.saveToken(any()) }.coAnswers { Unit.asSuccess() }
        coEvery {
            apiDataSource.login(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.DiskError.asFailure() }
    }

    private fun givenApiReturnsError() {
        coEvery {
            apiDataSource.login(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.NetworkError.asFailure() }
    }
}