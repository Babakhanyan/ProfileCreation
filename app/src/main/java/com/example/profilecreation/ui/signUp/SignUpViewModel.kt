package com.example.profilecreation.ui.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.common.dataOrNull
import com.example.data.UserService
import com.example.profilecreation.uiMapper.PortfolioUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userService: UserService,
    private val portfolioUiMapper: PortfolioUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state

    init {
        viewModelScope.launch {
            val portfolio = userService.getPortfolio().first()
            _state.emit(SignUpState(UIModel.Data(portfolioUiMapper.mapToUi(portfolio))))
        }
    }

    fun processIntent(signUpIntent: SignUpIntent) {
        val data = _state.value.uiState.dataOrNull() ?: return
        viewModelScope.launch {
            when (signUpIntent) {
                is UpdateEmail -> _state.emit(
                    _state.value.copy(
                        uiState = UIModel.Data(
                            data.copy(
                                emailFieldState = data.emailFieldState.copy(
                                    text = signUpIntent.email,
                                    isError = false
                                )
                            )
                        )
                    )

                )

                is UpdateFirstName -> _state.emit(
                    _state.value.copy(
                        uiState = UIModel.Data(
                            data.copy(
                                firstNameFieldState = data.firstNameFieldState.copy(
                                    text = signUpIntent.firstName,
                                    isError = false
                                )
                            )
                        )
                    )
                )

                is UpdatePassword -> _state.emit(
                    _state.value.copy(
                        uiState = UIModel.Data(
                            data.copy(
                                passwordFieldState = data.passwordFieldState.copy(
                                    text = signUpIntent.password,
                                    isError = false
                                )
                            )
                        )
                    )
                )

                is UpdateUrl -> _state.emit(
                    _state.value.copy(
                        uiState = UIModel.Data(
                            data.copy(
                                urlFieldState = data.urlFieldState.copy(
                                    text = signUpIntent.url,
                                    isError = false
                                )
                            )
                        )
                    )
                )

                is UpdateUri -> _state.emit(SignUpState(UIModel.Data(data.copy(avatarUri = signUpIntent.avatarUri))))

                Submit -> {
                    val portfolioUi = data.checkPortfolioUIValidation()
                    if (portfolioUi.hasAnyError()) {
                        _state.emit(SignUpState(UIModel.Data(portfolioUi)))
                    } else {
                        userService.savePortfolio(portfolioUiMapper.mapToDomain(data))
                        _state.emit(_state.value.copy(navigationEvent = NavigationEvent.NavigateToConfirmationPage))
                    }
                }

                ResetNavigation -> _state.emit(_state.value.copy(navigationEvent = NavigationEvent.None))
            }
        }
    }
}

data class SignUpState(
    val uiState: UIModel<PortfolioUi> = UIModel.Loading,
    val navigationEvent: NavigationEvent = NavigationEvent.None
)

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

sealed interface SignUpIntent
class UpdateUri(val avatarUri: String) : SignUpIntent
class UpdateFirstName(val firstName: String) : SignUpIntent
class UpdateEmail(val email: String) : SignUpIntent
class UpdatePassword(val password: String) : SignUpIntent
class UpdateUrl(val url: String) : SignUpIntent
object Submit : SignUpIntent
object ResetNavigation : SignUpIntent

sealed class NavigationEvent {
    object NavigateToConfirmationPage : NavigationEvent()
    object None : NavigationEvent()
}
