package com.example.profilecreation.ui.confirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.UIModel
import com.example.data.UserService
import com.example.domain.Portfolio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    userService: UserService
) : ViewModel() {

    private val _uiModel: StateFlow<UIModel<Portfolio>> =
        userService.getPortfolio().map { UIModel.Data(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, UIModel.Loading)

    val uiModel: Flow<UIModel<Portfolio>> = _uiModel
}