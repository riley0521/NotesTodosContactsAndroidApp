package com.rpfcoding.notestodoscontactsapp.core.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import com.rpfcoding.notestodoscontactsapp.R
import com.rpfcoding.notestodoscontactsapp.destinations.ContactScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.NotesScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.SettingsScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.TodoScreenDestination

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Notes(NotesScreenDestination, Icons.Filled.Notes, R.string.notes),
    Todo(TodoScreenDestination, Icons.Filled.List, R.string.todo),
    Contacts(ContactScreenDestination, Icons.Filled.Group, R.string.contacts),
    Settings(SettingsScreenDestination, Icons.Filled.Settings, R.string.settings)
}