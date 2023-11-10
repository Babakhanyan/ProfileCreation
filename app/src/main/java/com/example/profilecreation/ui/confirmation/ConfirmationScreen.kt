package com.example.profilecreation.ui.confirmation

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.UIModel
import com.example.profilecreation.R
import com.example.profilecreation.ui.composeable.ClickableLinkText
import com.example.profilecreation.ui.composeable.Description
import com.example.profilecreation.ui.composeable.Email
import com.example.profilecreation.ui.composeable.ImageComponent
import com.example.profilecreation.ui.composeable.Name
import com.example.profilecreation.ui.composeable.ProgressBar
import com.example.profilecreation.ui.composeable.RippleButton
import com.example.profilecreation.ui.composeable.Title
import com.example.profilecreation.ui.signUp.PortfolioUi
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ConfirmationScreen(
    viewModel: ConfirmationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Surface {
        when (val portfolioUi = state.uiState) {
            is UIModel.Data -> ConfirmationScreenContent(portfolioUi.data)
            UIModel.Loading -> ProgressBar()
        }
    }
}

@Composable
fun ConfirmationScreenContent(portfolioUi: PortfolioUi) {

    val context = LocalContext.current

    val avatarState = remember(portfolioUi.avatarUri) {
        mutableStateOf(portfolioUi.avatarUri)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.margin_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        Title(
            title = stringResource(
                id = R.string.confirmation_title, portfolioUi.firstNameFieldState.text
            )
        )
        Description(stringResource(id = R.string.confirmation_description))
        ImageComponent(avatarState)
        Name(portfolioUi.firstNameFieldState.text)
        Email(portfolioUi.emailFieldState.text)
        ClickableLinkText(portfolioUi.urlFieldState.text)
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_extra_big)))
        RippleButton(stringResource(id = R.string.confirmation_sign_in)) {
            Toast.makeText(context, "Sign in pressed", Toast.LENGTH_SHORT).show()
        }
    }
}
