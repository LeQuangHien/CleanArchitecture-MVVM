package com.hienle.cleanarchitecture

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hienle.cleanarchitecture.feature.news.article.NewsScreen
import com.hienle.cleanarchitecture.feature.news.webview.WebViewScreen
import java.net.URLEncoder

/**
 * enum values that represent the screens in the app
 */
enum class CaScreen(
    @StringRes val title: Int,
) {
    Start(title = R.string.app_name),
    Details(title = R.string.details_screen),
    Favorites(title = R.string.favorites_screen),
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
                        imageVector = Icons.Filled.ArrowBack,
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
    // Get the name of the current screen
    val currentScreen =
        CaScreen.valueOf(
            backStackEntry?.destination?.route?.split("/")?.first() ?: CaScreen.Start.name,
        )

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
            startDestination = CaScreen.Start.name,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            composable(route = CaScreen.Start.name) {
                NewsScreen(
                    onArticleClicked = { url ->
                        val encodedUrl = URLEncoder.encode(url, Charsets.UTF_8.name())
                        navController.navigate("${CaScreen.Details.name}/$encodedUrl")
                    },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.padding_medium)),
                )
            }
            composable(
                route = "${CaScreen.Details.name}/{url}",
                arguments = listOf(navArgument("url") { type = NavType.StringType }),
            ) {
                val url = backStackEntry?.arguments?.getString("url") ?: ""
                WebViewScreen(
                    url = url,
                    modifier = Modifier.fillMaxHeight(),
                )
            }
            composable(route = CaScreen.Favorites.name) {
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
