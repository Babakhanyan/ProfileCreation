package com.example.profilecreation.ui.composeable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import com.example.profilecreation.ui.signUp.FieldState

@Composable
fun CustomTextField(
    fieldState: State<FieldState>,
    errorMessage: String,
    label: String,
    isLastTextField: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {

    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        textStyle = MaterialTheme.typography.bodyMedium,
        value = fieldState.value.text,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
        ),
        singleLine = true,
        isError = fieldState.value.isError,
        supportingText = {
            if (fieldState.value.isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (fieldState.value.isError) Icon(
                Icons.Filled.Info,
                "error",
                tint = MaterialTheme.colorScheme.error
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = if (isLastTextField) ImeAction.Done else ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
    )
}
