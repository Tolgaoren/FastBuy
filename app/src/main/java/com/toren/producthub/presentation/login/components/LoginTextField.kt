package com.toren.producthub.presentation.login.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp


@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    onImeAction: () -> Unit = {},
    trailingIcon: Painter? = null,
    visibility: Boolean = false,
    onTogglePasswordVisibility: () -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 16.sp) },
        trailingIcon = {
            trailingIcon?.let {
                IconButton(onClick = { onTogglePasswordVisibility() }) {
                    Icon(
                        painter = it,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                onImeAction()
            }
        ),
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}
