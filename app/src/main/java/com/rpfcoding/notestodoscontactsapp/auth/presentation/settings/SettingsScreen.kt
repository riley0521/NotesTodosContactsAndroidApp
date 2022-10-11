package com.rpfcoding.notestodoscontactsapp.auth.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultCard
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultDialogButton
import com.rpfcoding.notestodoscontactsapp.destinations.*
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    deleteAccountResult: ResultRecipient<DeleteAccountDialogDestination, Boolean>,
    signOutResult: ResultRecipient<SignOutDialogDestination, Boolean>
) {
    val state = viewModel.state

    deleteAccountResult.onNavResult {
        when (it) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> {
                if(it.value) {
                    viewModel.onEvent(SettingsViewModel.SettingsEvent.ClickedDeleteAccount)
                }
            }
        }
    }

    signOutResult.onNavResult {
        when(it) {
            NavResult.Canceled -> Unit
            is NavResult.Value -> {
                if(it.value) {
                    viewModel.onEvent(SettingsViewModel.SettingsEvent.ClickedSignOut)
                }
            }
        }
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.eventChannel) {
        viewModel.eventChannel.collectLatest { event ->
            when(event) {
                SettingsViewModel.UiEvent.NavigateToHome -> {
                    navigator.popBackStack(SettingsScreenDestination.route, true)
                    navigator.navigate(NotesScreenDestination)
                }
                is SettingsViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    LaunchedEffect(key1 = state.isAnonymousAccount) {
        Toast.makeText(context, state.isAnonymousAccount.toString(), Toast.LENGTH_SHORT).show()
    }

    SettingsContent(
        isAnonymousAccount = state.isAnonymousAccount,
        onSignInClick = {
            navigator.navigate(LoginScreenDestination)
        },
        onCreateAccountClick = {
            navigator.navigate(SignUpScreenDestination)
        },
        onSignOutClick = {
            navigator.navigate(SignOutDialogDestination)
        },
        onDeleteAccountClick = {
            navigator.navigate(DeleteAccountDialogDestination)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsContent(
    isAnonymousAccount: Boolean,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteAccountClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isAnonymousAccount) {
            DefaultCard(
                title = R.string.card_sign_in,
                icon = Icons.Filled.Login,
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth()
            )

            DefaultCard(
                title = R.string.card_create_account,
                icon = Icons.Filled.Create,
                onClick = onCreateAccountClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        } else {
            DefaultCard(
                title = R.string.card_sign_out,
                icon = Icons.Filled.Logout,
                onClick = onSignOutClick,
                modifier = Modifier
                    .fillMaxWidth()
            )

            DefaultCard(
                title = R.string.card_delete_account,
                icon = Icons.Filled.Delete,
                onClick = onDeleteAccountClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DeleteAccountDialog(
    resultNavigator: ResultBackNavigator<Boolean>
) {
    DeleteAccountDialogContent {
        resultNavigator.navigateBack(it)
    }
}

@Composable
fun DeleteAccountDialogContent(
    onAction: (Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.confirm_delete_account)) },
        confirmButton = {
            DefaultDialogButton(
                title = R.string.dialog_confirm,
                action = {
                    onAction(true)
                },
                backgroundColor = Color.Transparent
            )
        },
        dismissButton = {
            DefaultDialogButton(
                title = R.string.dialog_cancel,
                action = {
                    onAction(false)
                },
                backgroundColor = Color.Transparent
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
    )
}

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun SignOutDialog(
    resultNavigator: ResultBackNavigator<Boolean>
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(id = R.string.confirm_sign_out)) },
        confirmButton = {
            DefaultDialogButton(
                title = R.string.dialog_confirm,
                action = {
                    resultNavigator.navigateBack(true)
                },
                backgroundColor = Color.Transparent
            )
        },
        dismissButton = {
            DefaultDialogButton(
                title = R.string.dialog_cancel,
                action = {
                    resultNavigator.navigateBack(false)
                },
                backgroundColor = Color.Transparent
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsContentPreview() {
    SettingsContent(
        isAnonymousAccount = false,
        onSignInClick = {},
        onCreateAccountClick = {},
        onSignOutClick = {},
        onDeleteAccountClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun DeleteAccountDialogContentPreview() {
    DeleteAccountDialogContent {

    }
}
