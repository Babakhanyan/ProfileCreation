package com.example.profilecreation.uiMapper

import com.example.domain.Portfolio
import com.example.profilecreation.ui.signUp.FieldState
import com.example.profilecreation.ui.signUp.PortfolioUi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class PortfolioUiMapper @Inject constructor() {
    fun mapToUi(portfolio: Portfolio): PortfolioUi {
        return PortfolioUi(
            avatarUri = portfolio.avatarUri,
            firstNameFieldState = FieldState(text = portfolio.firstName),
            emailFieldState = FieldState(text = portfolio.emailAddress),
            passwordFieldState = FieldState(text = portfolio.password),
            urlFieldState = FieldState(text = portfolio.url)
        )
    }

    fun mapToDomain(portfolioUi: PortfolioUi): Portfolio {
        return Portfolio(
            avatarUri = portfolioUi.avatarUri,
            firstName = portfolioUi.firstNameFieldState.text,
            emailAddress = portfolioUi.emailFieldState.text,
            password = portfolioUi.passwordFieldState.text,
            url = portfolioUi.urlFieldState.text,
        )
    }
}
