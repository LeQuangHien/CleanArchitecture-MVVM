package com.hienle.cleanarchitecture

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hienle.cleanarchitecture.feature.news.article.NewsScreen
import com.hienle.cleanarchitecture.feature.news.webview.WebViewScreen
import kotlinx.serialization.Serializable

@Serializable
object Start

@Serializable
data class Details(val url: String)

@Serializable
object Favorites

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(currentScreen) },
        colors =
            TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
    )
}

@Composable
fun CaApp(navController: NavHostController = rememberNavController()) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the current screen from the back stack entry's route
    val currentScreen = backStackEntry?.destination?.let { currentDestination ->
        when {
            currentDestination.hasRoute(Start::class) -> "Start"
            currentDestination.hasRoute(Details::class) -> "Details"
            currentDestination.hasRoute(Favorites::class) -> "Favorites"
            else -> "Start"
        }
    } ?: "Start"

    Scaffold(
        topBar = {
            CaAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
            )
        },
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Start,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            composable<Start> {
                NewsScreen(
                    onArticleClicked = { url ->
                        navController.navigate(Details(url))
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
            composable<Details> { navBackStackEntry ->
                val details: Details = navBackStackEntry.toRoute()
                WebViewScreen(
                    url = details.url,
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable<Favorites> {
                FavoritesScreen()
            }
        }
    }
}

@Composable
fun FavoritesScreen() {
    Surface {
        Text(text = "My Favorites")
    }
}
