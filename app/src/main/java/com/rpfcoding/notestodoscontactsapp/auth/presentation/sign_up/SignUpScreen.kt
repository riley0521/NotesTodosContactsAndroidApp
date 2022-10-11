package com.rpfcoding.notestodoscontactsapp.auth.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultTextField
import com.rpfcoding.notestodoscontactsapp.core.components.BasicAppBar
import com.rpfcoding.notestodoscontactsapp.core.components.DefaultButton
import com.rpfcoding.notestodoscontactsapp.core.util.UiText
import com.rpfcoding.notestodoscontactsapp.destinations.SettingsScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.SignUpScreenDestination
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val state = viewModel.state

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.eventChannel) {
        viewModel.eventChannel.collectLatest { event ->
            when (event) {
                SignUpViewModel.UiEvent.NavigateBack -> {
                    navigator.popBackStack(SignUpScreenDestination.route, true)
                    navigator.navigate(SettingsScreenDestination)
                }
                is SignUpViewModel.UiEvent.ShowMessage -> {
                    Toast.makeText(context, event.message.asString(context), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    BasicAppBar(title = R.string.create_account)

    SignUpContent(
        isLoading = state.isLoading,
        email = state.email,
        emailError = state.emailError,
        password = state.password,
        passwordError = state.passwordError,
        confirmPass = state.confirmPass,
        confirmPassError = state.confirmPassError,
        onEmailChange = {
            viewModel.onEvent(SignUpViewModel.SignUpEvent.EnteredEmail(it))
        },
        onPasswordChange = {
            viewModel.onEvent(SignUpViewModel.SignUpEvent.EnteredPassword(it))
        },
        onConfirmPassChange = {
            viewModel.onEvent(SignUpViewModel.SignUpEvent.EnteredConfirmPassword(it))
        },
        onSignUpClick = {
            viewModel.onEvent(SignUpViewModel.SignUpEvent.ClickedSignUp)
        }
    )
}

@Composable
fun SignUpContent(
    isLoading: Boolean,
    email: String,
    emailError: UiText? = null,
    password: String,
    passwordError: UiText? = null,
    confirmPass: String,
    confirmPassError: UiText? = null,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPassChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultTextField(
            value = email,
            onValueChange = onEmailChange,
            hint = stringResource(id = R.string.hint_email),
            leadingIcon = Icons.Filled.Email,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = emailError.asString(),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = stringResource(id = R.string.hint_password),
            leadingIcon = Icons.Filled.Lock,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (passwordError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = passwordError.asString(),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            value = confirmPass,
            onValueChange = onConfirmPassChange,
            hint = stringResource(id = R.string.hint_confirm_password),
            leadingIcon = Icons.Filled.Lock,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        if (confirmPassError != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = confirmPassError.asString(),
                color = MaterialTheme.colors.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        DefaultButton(
            title = R.string.btn_create_account,
            action = onSignUpClick,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpContentPreview() {
    BasicAppBar(title = R.string.create_account)

    SignUpContent(
        isLoading = true,
        email = "",
        emailError = UiText.StringResource(resId = R.string.unknown_error),
        password = "",
        passwordError = UiText.StringResource(resId = R.string.unknown_error),
        confirmPass = "",
        confirmPassError = UiText.StringResource(resId = R.string.unknown_error),
        onEmailChange = {},
        onPasswordChange = {},
        onConfirmPassChange = {},
        onSignUpClick = {}
    )
}
