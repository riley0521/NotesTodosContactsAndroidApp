package com.rpfcoding.notestodoscontactsapp.note.presentation.add_edit_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.note.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.note.presentation.add_edit_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Destination
@Composable
fun AddEditNoteScreen(
    note: Note?,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    val titleState = viewModel.noteTitle
    val descState = viewModel.noteDescription

    val scaffoldState = rememberScaffoldState()

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(
                if (noteColor != -1) {
                    noteColor
                } else {
                    viewModel.noteColor
                }
            )
        )
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventChannel.collectLatest { event ->
            when(event) {
                AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navigator.popBackStack()
                }
                is AddEditNoteViewModel.UiEvent.ShowErrorMessage -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteViewModel.AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Filled.Save,
                    contentDescription = stringResource(id = R.string.save_note)
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteColors.forEach { color ->
                    val colorInt = color.toArgb()

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 3.dp,
                                color = if (viewModel.noteColor == colorInt) Color.Black else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                }
                                viewModel.onEvent(
                                    AddEditNoteViewModel.AddEditNoteEvent.ChangeColor(
                                        colorInt
                                    )
                                )
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint?.asString() ?: stringResource(id = R.string.hint_title),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteViewModel.AddEditNoteEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteViewModel.AddEditNoteEvent.ChangeTitleFocus(it))
                },
                textStyle = MaterialTheme.typography.h5,
                isHintVisible = titleState.isHintVisible,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = descState.text,
                hint = descState.hint?.asString() ?: stringResource(id = R.string.hint_description),
                onValueChange = {
                    viewModel.onEvent(AddEditNoteViewModel.AddEditNoteEvent.EnteredDescription(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteViewModel.AddEditNoteEvent.ChangeDescriptionFocus(it))
                },
                textStyle = MaterialTheme.typography.body1,
                isHintVisible = descState.isHintVisible,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}