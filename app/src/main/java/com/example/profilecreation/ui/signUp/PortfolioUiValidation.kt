package com.example.profilecreation.ui.signUp

import android.webkit.URLUtil
import com.example.common.isValidEmail

fun PortfolioUi.checkPortfolioUIValidation(): PortfolioUi {
    var data = this

    if (data.firstNameFieldState.text.isEmpty()) {
        data = data.copy(firstNameFieldState = data.firstNameFieldState.copy(isError = true))
    }

    if (!isValidEmail(data.emailFieldState.text)) {
        data = data.copy(emailFieldState = data.emailFieldState.copy(isError = true))
    }

    if (data.passwordFieldState.text.isEmpty()) {
        data = data.copy(passwordFieldState = data.passwordFieldState.copy(isError = true))
    }

    if (!URLUtil.isValidUrl(data.urlFieldState.text)) {
        data = data.copy(urlFieldState = data.urlFieldState.copy(isError = true))
    }

    return data
}

fun PortfolioUi.hasAnyError(): Boolean {
    if (this.firstNameFieldState.isError
        || this.passwordFieldState.isError
        || this.urlFieldState.isError
        || this.emailFieldState.isError
    ) {
        return true
    }

    return false
}
