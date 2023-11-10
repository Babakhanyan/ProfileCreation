package com.example.profilecreation.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.data.UserService
import com.example.profilecreation.ui.signUp.PortfolioUi
import com.example.profilecreation.uiMapper.PortfolioUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    userService: UserService,
    portfolioUiMapper: PortfolioUiMapper,
) : ViewModel() {

    private val _state = MutableStateFlow(ConfirmationState())
    val state: StateFlow<ConfirmationState> = _state

    init {
        viewModelScope.launch {
            _state.emit(
                ConfirmationState(
                    UIModel.Data(
                        portfolioUiMapper.mapToUi(
                            userService.getPortfolio().first()
                        )
                    )
                )
            )
        }
    }
}

data class ConfirmationState(
    val uiState: UIModel<PortfolioUi> = UIModel.Loading,
)