package com.hienle.cleanarchitecture

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hienle.cleanarchitecture.feature.news.article.NewsScreen
import com.hienle.cleanarchitecture.feature.news.webview.WebViewScreen
import java.net.URLEncoder

// Define sealed classes for type-safe navigation destinations
sealed class CaScreen(@StringRes val title: Int, val route: String) {
    object Start : CaScreen(R.string.app_name, "start")
    object Details : CaScreen(R.string.details_screen, "details/{url}") {
        fun createRoute(url: String) = "details/$url"
    }
    object Favorites : CaScreen(R.string.favorites_screen, "favorites")
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaAppBar(
    currentScreen: CaScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
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
    val currentScreen =
        when (backStackEntry?.destination?.route?.substringBefore("/")) {
            CaScreen.Start.route -> CaScreen.Start
            CaScreen.Details.route.substringBefore("/{url}") -> CaScreen.Details
            CaScreen.Favorites.route -> CaScreen.Favorites
            else -> CaScreen.Start
        }

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
            startDestination = CaScreen.Start.route,
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(route = CaScreen.Start.route) {
                NewsScreen(
                    onArticleClicked = { url ->
                        val encodedUrl = URLEncoder.encode(url, Charsets.UTF_8.name())
                        navController.navigate(CaScreen.Details.createRoute(encodedUrl))
                    },
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
            composable(
                route = CaScreen.Details.route,
                arguments = listOf(navArgument("url") { type = NavType.StringType }),
            ) { navBackStackEntry ->
                val url = navBackStackEntry.arguments?.getString("url") ?: ""
                WebViewScreen(
                    url = url,
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route = CaScreen.Favorites.route) {
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
