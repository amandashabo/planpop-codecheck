package net.sevendays.android.code_check.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import net.sevendays.android.code_check.R
import net.sevendays.android.code_check.item.RepositoryItem

@Composable
internal fun ItemsSearchListScreen(
    items: List<RepositoryItem>,
    searches: List<String>,
    searchQuery: String,
    viewState: ViewState,
    onItemClicked: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    onCancel: () -> Unit,
    fetchResults: (String) -> Unit
) {
    val snackBarHostState = SnackbarHostState()
    val errorMessage = stringResource(id = R.string.error)
    if (viewState == ViewState.ERROR) {
        LaunchedEffect(snackBarHostState) {
            snackBarHostState.showSnackbar(message = errorMessage)
        }
    }
    Scaffold(
        topBar = {
            Column {
                Search(
                    searchQuery = searchQuery,
                    searches = searches,
                    onTextChanged = onTextChanged,
                    onCancel = onCancel,
                    fetchResults = fetchResults
                )
            }
        },
        backgroundColor = ThemeColors.background(),
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { padding ->
        when (viewState) {
            ViewState.LOADING -> {
                LoadingIndicator()
            }
            ViewState.DONE -> {
                ItemList(
                    modifier = Modifier.padding(padding),
                    items = items,
                    onItemClicked = onItemClicked
                )
            }
            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    searchQuery: String,
    searches: List<String>,
    onTextChanged: (String) -> Unit,
    onCancel: () -> Unit,
    fetchResults: (String) -> Unit
) {
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = if (active) 0.dp else Padding.small),
        query = searchQuery,
        onQueryChange = onTextChanged,
        onSearch = {
            fetchResults(it)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(
                modifier = Modifier.alpha(0.5f),
                text = stringResource(R.string.searchInputText_hint)
            )
        },
        leadingIcon = {
            IconButton(
                modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        },
        trailingIcon = {
            if (active) {
                IconButton(
                    onClick = {
                        if (searchQuery == "") {
                            active = false
                        } else {
                            onCancel()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(Padding.medium),
            verticalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            searches.forEach { search ->
                item {
                    Text(
                        modifier = Modifier.clickable {
                            fetchResults(search)
                            onTextChanged(search)
                            active = false
                        },
                        text = search
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemList(
    modifier: Modifier,
    items: List<RepositoryItem>,
    onItemClicked: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(Padding.medium),
        verticalArrangement = Arrangement.spacedBy(Padding.medium)
    ) {
        items.forEach { i ->
            item {
                Column(
                    modifier = Modifier
                        .padding(vertical = Padding.small)
                        .clickable { onItemClicked(i.name) }
                ) {
                    Text(
                        text = i.fullName,
                        color = ThemeColors.text()
                    )
                    if (i != items.last()) {
                        SectionDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.25f)
            .width(1.dp),
        color = Color.Gray
    )
}

@Composable
private fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.purple_500)
        )
    }
}
