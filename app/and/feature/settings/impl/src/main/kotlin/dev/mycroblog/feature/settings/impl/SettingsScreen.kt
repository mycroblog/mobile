package dev.mycroblog.feature.settings.impl

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.mycroblog.core.designsystem.NavigateUpButton

/** The settings: for now, the action that adds an account ([onAddAccount]). With [onUp], it has an Up button. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onAddAccount: () -> Unit, modifier: Modifier = Modifier, onUp: (() -> Unit)? = null) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
            )
        },
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                ListItem(modifier = Modifier.clickable(onClick = onAddAccount)) {
                    Text(stringResource(R.string.settings_add_account))
                }
            }
        }
    }
}
