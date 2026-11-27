package dev.mycroblog.app.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import dev.mycroblog.app.R
import kotlinx.serialization.Serializable

/** The destination of the navigation bar's "More" item: the top-level destinations the bar has no room for. */
@Serializable
data object MoreKey : NavKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoreScreen(
    destinations: List<TopLevelDestination>,
    onSelect: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = modifier, topBar = { TopAppBar(title = { Text(stringResource(R.string.destination_more)) }) }) {
        LazyColumn(contentPadding = it) {
            items(destinations) { destination ->
                ListItem(
                    leadingContent = { Icon(destination.icon, contentDescription = null) },
                    modifier = Modifier.clickable { onSelect(destination) },
                ) {
                    Text(stringResource(destination.label))
                }
            }
        }
    }
}
