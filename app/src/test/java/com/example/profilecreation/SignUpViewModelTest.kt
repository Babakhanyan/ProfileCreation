package com.example.profilecreation

import app.cash.turbine.test
import com.example.common.MainDispatcherRule
import com.example.common.dataOrNull
import com.example.data.UserService
import com.example.fake.getFakePortfolio
import com.example.profilecreation.ui.signUp.SignUpViewModel
import com.example.profilecreation.ui.signUp.UpdateFirstName
import com.example.profilecreation.uiMapper.PortfolioUiMapper
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class SignUpViewModelTest {

    @get:Rule
    val coroutinesTestRule = MainDispatcherRule()

    @Test
    fun `Check viewModel uiState when viewModel created`() = runTest {

        val service: UserService = mockk(relaxed = true)

        val expectedPortfolio = getFakePortfolio()

        every { service.getPortfolio() } returns flowOf(expectedPortfolio)

        val viewModel = SignUpViewModel(service, PortfolioUiMapper())

        val expectedPortfolioUi = PortfolioUiMapper().mapToUi(expectedPortfolio)

        viewModel.state.test {
            Assert.assertEquals(awaitItem().uiState.dataOrNull(), expectedPortfolioUi)
        }
    }

    @Test
    fun `Check viewModel uiState after called intent UpdateFirstName`() = runTest {

        val expectedName = "Anna"

        val service: UserService = mockk(relaxed = true)
        every { service.getPortfolio() } returns flowOf(getFakePortfolio(firstName = "John"))

        val viewModel = SignUpViewModel(service, PortfolioUiMapper())

        viewModel.processIntent(UpdateFirstName(expectedName))

        val expectedPortfolioUi =
            PortfolioUiMapper().mapToUi(getFakePortfolio(firstName = expectedName))

        viewModel.state.test {
            Assert.assertEquals(awaitItem().uiState.dataOrNull(), expectedPortfolioUi)
        }
    }
}