package com.rpfcoding.notestodoscontactsapp.core.components

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.rpfcoding.notestodoscontactsapp.NavGraphs
import com.rpfcoding.notestodoscontactsapp.ui.theme.NotesTodosContactsAppTheme

@Composable
fun MyAppScreen() {
    NotesTodosContactsAppTheme {
        DestinationsNavHost(navGraph = NavGraphs.root)
    }
}