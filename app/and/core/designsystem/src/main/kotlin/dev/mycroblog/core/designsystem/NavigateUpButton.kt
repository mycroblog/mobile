package dev.mycroblog.core.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

/** The Up button of a top app bar, which calls [onClick]. */
@Composable
fun NavigateUpButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    // The button, not its icon, carries the name, so that its node is the 48dp touch target.
    val label = stringResource(R.string.navigate_up)
    IconButton(onClick = onClick, modifier = modifier.semantics { contentDescription = label }) {
        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
    }
}
