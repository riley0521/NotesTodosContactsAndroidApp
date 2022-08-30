package com.rpfcoding.notestodoscontactsapp.auth.presentation.login.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DefaultTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    leadingIcon: ImageVector?,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = hint)
        },
        leadingIcon = {
            leadingIcon?.let {
                Icon(imageVector = it, contentDescription = null)
            }
        },
        trailingIcon = {
            if (visualTransformation is PasswordVisualTransformation) {
                Icon(
                    imageVector = if (isVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            isVisible = !isVisible
                        }
                )
            }
        },
        singleLine = singleLine,
        modifier = modifier
    )
}