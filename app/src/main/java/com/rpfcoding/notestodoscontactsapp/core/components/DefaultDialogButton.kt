package com.rpfcoding.notestodoscontactsapp.core.components

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun DefaultDialogButton(
    @StringRes title: Int,
    action: () -> Unit,
    backgroundColor: Color
) {
    TextButton(
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Black
        ),
    ) {
        Text(text = stringResource(id = title))
    }
}