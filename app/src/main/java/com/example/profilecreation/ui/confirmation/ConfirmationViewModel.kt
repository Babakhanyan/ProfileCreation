package com.example.profilecreation.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.data.UserService
import com.example.profilecreation.ui.signUp.PortfolioUi
import com.example.profilecreation.uiMapper.PortfolioUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    userService: UserService,
    portfolioUiMapper: PortfolioUiMapper,
) : ViewModel() {

    private val _uiModel: StateFlow<UIModel<PortfolioUi>> =
        userService.getPortfolio().map { UIModel.Data(portfolioUiMapper.mapToUi(it)) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UIModel.Loading)

    val uiModel: Flow<UIModel<PortfolioUi>> = _uiModel
}