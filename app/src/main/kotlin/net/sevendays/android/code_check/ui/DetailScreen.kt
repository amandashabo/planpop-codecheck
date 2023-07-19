/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package net.sevendays.android.code_check.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import net.sevendays.android.code_check.R
import net.sevendays.android.code_check.item.RepositoryItem

@Composable
internal fun DetailScreen(item: RepositoryItem, onBack: () -> Unit) {
    Scaffold(
        topBar = { BackButton(onBack = onBack) },
        backgroundColor = ThemeColors.background()
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(Padding.large)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageAndTitle(item = item)
            MetaData(item = item)
        }
    }
}

@Composable
private fun BackButton(onBack: () -> Unit) {
    IconButton(onClick = onBack) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            tint = ThemeColors.text(),
            contentDescription = "Back Icon"
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ImageAndTitle(item: RepositoryItem) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            model = item.ownerIconUrl.avatar_url,
            contentDescription = "Item Image",
            modifier = Modifier.size(240.dp)
        )
        Text(
            text = item.fullName,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = Padding.large),
            fontSize = FontSize.large,
            color = ThemeColors.text()
        )
    }
}

@Composable
private fun MetaData(item: RepositoryItem) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = item.language,
            fontSize = FontSize.medium,
            color = ThemeColors.text()
        )
        Spacer(Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(Padding.small)
        ) {
            DataText(text = stringResource(id = R.string.num_stars, item.stargazersCount))
            DataText(text = stringResource(id = R.string.num_watchers, item.watchersCount),)
            DataText(text = stringResource(id = R.string.num_forks, item.forksCount))
            DataText(text = stringResource(id = R.string.num_open_issues, item.openIssuesCount))
        }
    }
}

@Composable
private fun DataText(text: String) {
    Text(
        text = text,
        fontSize = FontSize.small,
        color = ThemeColors.text()
    )
}