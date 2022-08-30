package com.rpfcoding.notestodoscontactsapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.rpfcoding.notestodoscontactsapp.destinations.ContactScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.NotesScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.TodoScreenDestination
import com.rpfcoding.notestodoscontactsapp.destinations.TypedDestination

@RootNavGraph(start = true)
@Destination
@Composable
fun BottomNavScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val scaffoldState = rememberScaffoldState()

    var isBottomBarVisible by remember {
        mutableStateOf(true)
    }

    when (navBackStackEntry?.destination?.route) {
        NotesScreenDestination.route, TodoScreenDestination.route, ContactScreenDestination.route -> {
            isBottomBarVisible = true
        }
        else -> {
            isBottomBarVisible = false
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        bottomBar = {
            MyBottomBar(navController = navController, isVisible = isBottomBarVisible)
        }
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            startRoute = NotesScreenDestination
        )
    }
}

@Composable
fun MyBottomBar(
    navController: NavController,
    isVisible: Boolean = true
) {
    val currentDestination: TypedDestination<out Any?> =
        navController.appCurrentDestinationAsState().value
            ?: NotesScreenDestination

    if (isVisible) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface
        ) {
            BottomBarDestination.values().forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.popBackStack(currentDestination.route, true)
                        navController.navigate(destination.direction) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(id = destination.label)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = destination.label))
                    }
                )
            }
        }
    }
}