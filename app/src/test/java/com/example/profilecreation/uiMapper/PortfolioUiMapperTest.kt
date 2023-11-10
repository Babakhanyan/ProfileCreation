package com.example.profilecreation.uiMapper

import com.example.fake.getFakePortfolio
import com.example.profilecreation.ui.signUp.FieldState
import com.example.profilecreation.ui.signUp.PortfolioUi
import org.junit.Assert
import org.junit.Test

internal class PortfolioUiMapperTest {

    @Test
    fun `Check Portfolio mapping to PortfolioUi`() {

        val portfolioUi = PortfolioUiMapper().mapToUi(getFakePortfolio())

        val expectedPortfolioUi = PortfolioUi(
            avatarUri = "path://",
            firstNameFieldState = FieldState(text = "John"),
            emailFieldState = FieldState(text = "John@gmail.com"),
            passwordFieldState = FieldState(text = "123456"),
            urlFieldState = FieldState(text = "http://www.google.com")
        )

        Assert.assertEquals(portfolioUi, expectedPortfolioUi)
    }

    @Test
    fun `Check PortfolioUi mapping to Portfolio`() {

        val portfolioUi = PortfolioUi(
            avatarUri = "path://",
            firstNameFieldState = FieldState(text = "John"),
            emailFieldState = FieldState(text = "John@gmail.com"),
            passwordFieldState = FieldState(text = "123456"),
            urlFieldState = FieldState(text = "http://www.google.com")
        )

        val portfolio = PortfolioUiMapper().mapToDomain(portfolioUi)

        val expectedPortfolio = getFakePortfolio()

        Assert.assertEquals(portfolio, expectedPortfolio)
    }
}
