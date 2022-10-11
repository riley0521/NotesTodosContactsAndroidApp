package com.rpfcoding.notestodoscontactsapp.core.components

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rpfcoding.notestodoscontactsapp.R

@Composable
fun BasicAppBar(
    @StringRes title: Int
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title)
            )
        },
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Preview
@Composable
fun BasicAppBarPreview() {
    BasicAppBar(title = R.string.unknown_error)
}