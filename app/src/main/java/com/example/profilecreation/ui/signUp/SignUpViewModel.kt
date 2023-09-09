package com.example.profilecreation.ui.signUp

import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.common.dataOrNull
import com.example.common.isValidEmail
import com.example.data.UserService
import com.example.domain.Portfolio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userService: UserService
) : ViewModel() {

    private val _command = Channel<Command>(Channel.BUFFERED)
    val command: Flow<Command> = _command.receiveAsFlow()

    private val _uiModel: MutableStateFlow<UIModel<Portfolio>> =
        MutableStateFlow(UIModel.Loading)
    val uiModel: Flow<UIModel<Portfolio>> = _uiModel.asStateFlow()

    private val _validationError: MutableStateFlow<ValidationError> =
        MutableStateFlow(ValidationError.None)
    val validationError: Flow<ValidationError> = _validationError.asStateFlow()

    init {
        viewModelScope.launch {
            _uiModel.emit(UIModel.Data(userService.getPortfolio().first()))
        }
    }

    fun updateAvatar(avatarUri: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _uiModel.tryEmit(UIModel.Data(data.copy(avatarUri = avatarUri)))
    }

    fun updateFirstName(firstName: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _validationError.tryEmit(ValidationError.None)
        _uiModel.tryEmit(UIModel.Data(data.copy(firstName = firstName)))
    }

    fun updateEmailAddress(emailAddress: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _validationError.tryEmit(ValidationError.None)
        _uiModel.tryEmit(UIModel.Data(data.copy(emailAddress = emailAddress)))
    }

    fun updatePassword(password: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _validationError.tryEmit(ValidationError.None)
        _uiModel.tryEmit(UIModel.Data(data.copy(password = password)))
    }

    fun updateWebSite(webSite: String) {
        val data = _uiModel.value.dataOrNull() ?: return
        _validationError.tryEmit(ValidationError.None)
        _uiModel.tryEmit(UIModel.Data(data.copy(webSite = webSite)))
    }

    fun submit() {
        val data = _uiModel.value.dataOrNull() ?: return
        if (isPortfolioValid(data)) {
            viewModelScope.launch {
                userService.savePortfolio(data)
                _command.trySend(Command.OpenConfirmationPage)
            }
        }
    }

    private fun isPortfolioValid(portfolio: Portfolio): Boolean {
        _validationError.tryEmit(ValidationError.None)

        if (portfolio.emailAddress.isEmpty()) {
            _validationError.tryEmit(ValidationError.RequiredEmailAddress)
            return false
        }

        if (!isValidEmail(portfolio.emailAddress)) {
            _validationError.tryEmit(ValidationError.InvalidEmailAddress)
            return false
        }

        if (portfolio.password.isEmpty()) {
            _validationError.tryEmit(ValidationError.RequiredPassword)
            return false
        }

        if (!URLUtil.isValidUrl(portfolio.webSite)) {
            _validationError.tryEmit(ValidationError.InvalidInvalidWebSite)
            return false
        }

        return true
    }
}

sealed class Command {
    object OpenConfirmationPage : Command()
}

sealed class ValidationError {
    object RequiredEmailAddress : ValidationError()
    object RequiredPassword : ValidationError()
    object InvalidEmailAddress : ValidationError()
    object InvalidInvalidWebSite : ValidationError()
    object None : ValidationError()
}