/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package net.sevendays.android.code_check.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.sevendays.android.code_check.ui.ItemsListRoute.Companion.itemRoute
import net.sevendays.android.code_check.ui.ItemsListRoute.Companion.listRoute

@Composable
internal fun ItemsSearchList() {
    val viewModel: ItemsViewModel = viewModel()
    val navController = rememberNavController()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val items by viewModel.items.collectAsState()
    val searches by viewModel.searches.collectAsState()
    val viewState by viewModel.viewState.collectAsState()
    NavHost(
        navController = navController,
        startDestination = listRoute
    ) {
        composable(route = listRoute) {
            ItemsSearchListScreen(
                items = items,
                searchQuery = searchQuery,
                searches = searches,
                viewState = viewState,
                onItemClicked = { id ->
                    navController.navigate(route = "$itemRoute/${id}")
                },
                onTextChanged = { viewModel.updateSearchQuery(it) },
                onCancel = { viewModel.updateSearchQuery("") },
                fetchResults = { viewModel.fetchResults(it) }
            )
        }
        composable(route = "$itemRoute/{id}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("id")
            if (itemId != null) {
                val item = items.firstOrNull { it.name == itemId }
                if (item != null) {
                    DetailScreen(item = item, onBack = { navController.popBackStack() } )
                }
            }
        }
    }
}

private class ItemsListRoute {

    companion object {

        const val listRoute = "List"
        const val itemRoute = "Item"

    }

}
