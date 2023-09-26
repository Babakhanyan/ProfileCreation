package com.example.profilecreation.ui.signUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.UIModel
import com.example.common.dataOrNull
import com.example.profilecreation.R
import com.example.profilecreation.ui.composeable.CameraComponent
import com.example.profilecreation.ui.composeable.CustomTextField
import com.example.profilecreation.ui.composeable.Description
import com.example.profilecreation.ui.composeable.ProgressBar
import com.example.profilecreation.ui.composeable.RippleButton
import com.example.profilecreation.ui.composeable.Title
import com.example.profilecreation.ui.destinations.ConfirmationScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    destinationsNavigator: DestinationsNavigator,
) {
    val navigateToDestination by viewModel.command.collectAsState(initial = null)
    navigateToDestination?.let {
        when (it) {
            Command.OpenConfirmationPage -> {
                destinationsNavigator.navigate(ConfirmationScreenDestination)
            }
        }
    }

    val uiModelState = viewModel.uiModel.collectAsState(initial = UIModel.Loading)

    Surface {
        when (uiModelState.value) {
            is UIModel.Data -> SignUpScreenContent(viewModel)
            UIModel.Loading -> ProgressBar()
        }
    }
}

@Composable
fun SignUpScreenContent(viewModel: SignUpViewModel) {
    val portfolioUi =
        viewModel.uiModel.collectAsState(initial = UIModel.Loading).value.dataOrNull() ?: return


    val avatarState = remember(portfolioUi.avatarUri) {
        mutableStateOf(portfolioUi.avatarUri)
    }
    val firstNameFieldState = remember(portfolioUi.firstNameFieldState) {
        mutableStateOf(portfolioUi.firstNameFieldState)
    }
    val emailFieldState = remember(portfolioUi.emailFieldState) {
        mutableStateOf(portfolioUi.emailFieldState)
    }
    val passwordFieldState = remember(portfolioUi.passwordFieldState) {
        mutableStateOf(portfolioUi.passwordFieldState)
    }
    val urlFieldState = remember(portfolioUi.urlFieldState) {
        mutableStateOf(portfolioUi.urlFieldState)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.margin_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Title(title = stringResource(id = R.string.sign_up_title))
        Description(stringResource(id = R.string.sign_up_description))
        CameraComponent(uriStr = avatarState) { viewModel.updateAvatar(it) }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_medium)))
        CustomTextField(
            fieldState = firstNameFieldState,
            errorMessage = stringResource(id = R.string.error_required),
            label = stringResource(id = R.string.sign_up_placeholder_first_name),
        ) { text -> viewModel.updateFirstName(text) }
        CustomTextField(
            fieldState = emailFieldState,
            errorMessage = stringResource(id = R.string.error_email_address),
            label = stringResource(id = R.string.sign_up_placeholder_email_address),
        ) { text -> viewModel.updateEmailAddress(text) }
        CustomTextField(
            fieldState = passwordFieldState,
            errorMessage = stringResource(id = R.string.error_password),
            label = stringResource(id = R.string.sign_up_placeholder_password),
        ) { text -> viewModel.updatePassword(text) }
        CustomTextField(
            fieldState = urlFieldState,
            errorMessage = stringResource(id = R.string.error_url),
            label = stringResource(id = R.string.sign_up_placeholder_url),
            isLastTextField = true
        ) { text -> viewModel.updateUrl(text) }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_extra_big)))
        RippleButton(text = stringResource(id = R.string.sign_up_submit)) { viewModel.submit() }
    }
}
