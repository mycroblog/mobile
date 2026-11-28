package dev.mycroblog.core.designsystem.post

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString

/**
 * One accessibility node for a button with a text [label]: its role, label and action on the button itself, where the
 * button's text would otherwise be a child node and the button a nameless view.
 */
internal fun Modifier.buttonSemantics(label: String, onClick: () -> Unit): Modifier = clearAndSetSemantics {
    role = Role.Button
    text = AnnotatedString(label)
    onClick {
        onClick()
        true
    }
}
