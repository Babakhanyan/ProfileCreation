package com.example.profilecreation.ui.signUp

import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.common.dataOrNull
import com.example.common.isValidEmail
import com.example.data.UserService
import com.example.profilecreation.uiMapper.PortfolioUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userService: UserService,
    private val portfolioUiMapper: PortfolioUiMapper,
) : ViewModel() {

    private val _command = Channel<Command>(Channel.BUFFERED)
    val command: Flow<Command> = _command.receiveAsFlow()

    private val _uiModel: MutableStateFlow<UIModel<PortfolioUi>> = MutableStateFlow(UIModel.Loading)
    val uiModel: Flow<UIModel<PortfolioUi>> = _uiModel.asStateFlow()

    init {
        viewModelScope.launch {
            val portfolio = userService.getPortfolio().first()
            _uiModel.emit(UIModel.Data(portfolioUiMapper.mapToUi(portfolio)))
        }
    }

    fun updateAvatar(avatarUri: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(UIModel.Data(data.copy(avatarUri = avatarUri)))
    }

    fun updateFirstName(firstName: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(
            UIModel.Data(
                data.copy(
                    firstNameFieldState = data.firstNameFieldState.copy(
                        text = firstName,
                        isError = false
                    )
                )
            )
        )
    }

    fun updateEmailAddress(emailAddress: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(
            UIModel.Data(
                data.copy(
                    emailFieldState = data.emailFieldState.copy(
                        text = emailAddress,
                        isError = false
                    )
                )
            )
        )
    }

    fun updatePassword(password: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(
            UIModel.Data(
                data.copy(
                    passwordFieldState = data.passwordFieldState.copy(
                        text = password,
                        isError = false
                    )
                )
            )
        )
    }

    fun updateUrl(url: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(
            UIModel.Data(
                data.copy(
                    urlFieldState = data.urlFieldState.copy(text = url, isError = false)
                )
            )
        )
    }

    fun submit() {
        val data = _uiModel.value.dataOrNull() ?: return
        if (isPortfolioValid(data)) {
            viewModelScope.launch {
                userService.savePortfolio(portfolioUiMapper.mapToDomain(data))
                _command.trySend(Command.OpenConfirmationPage)
            }
        }
    }

    private fun isPortfolioValid(portfolio: PortfolioUi): Boolean {
        var data = _uiModel.value.dataOrNull() ?: return false
        var isPortfolioValid = true
        if (portfolio.firstNameFieldState.text.isEmpty()) {
            data = data.copy(firstNameFieldState = data.firstNameFieldState.copy(isError = true))
            isPortfolioValid = false
        }

        if (!isValidEmail(portfolio.emailFieldState.text)) {
            data = data.copy(emailFieldState = data.emailFieldState.copy(isError = true))
            isPortfolioValid = false
        }

        if (portfolio.passwordFieldState.text.isEmpty()) {
            data = data.copy(passwordFieldState = data.passwordFieldState.copy(isError = true))
            isPortfolioValid = false
        }

        if (!URLUtil.isValidUrl(portfolio.urlFieldState.text)) {
            data = data.copy(urlFieldState = data.urlFieldState.copy(isError = true))
            isPortfolioValid = false
        }

        _uiModel.tryEmit(UIModel.Data(data))

        return isPortfolioValid
    }

    fun onAvatarClick() {
        _command.trySend(Command.OpenCamera)
    }
}

data class FieldState(
    val text: String = "",
    val isError: Boolean = false,
)

data class PortfolioUi(
    val avatarUri: String,
    val firstNameFieldState: FieldState,
    val emailFieldState: FieldState,
    val passwordFieldState: FieldState,
    val urlFieldState: FieldState,
)

sealed class Command {
    object OpenConfirmationPage : Command()
    object OpenCamera : Command()
}