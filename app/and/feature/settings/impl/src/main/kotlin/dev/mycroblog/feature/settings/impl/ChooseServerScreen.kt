package dev.mycroblog.feature.settings.impl

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import dev.mycroblog.core.designsystem.NavigateUpButton
import dev.mycroblog.core.model.InstanceLookupFailure
import dev.mycroblog.core.model.ServerAddress

/**
 * The server screen with its ViewModel: the address field's text is saved with the screen and sent to the ViewModel.
 * Before a lookup of an address of the local network, it asks for [ACCESS_LOCAL_NETWORK] when the app needs it, and
 * looks up whatever the answer. [onRead] reads the server that was found without an account.
 */
@Composable
internal fun ChooseServerRoute(
    onUp: (() -> Unit)?,
    onRead: (ServerAddress) -> Unit,
    viewModel: ChooseServerViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val address = rememberTextFieldState()
    LaunchedEffect(address) {
        snapshotFlow { address.text.toString() }.collect(viewModel::onAddressChange)
    }
    val context = LocalContext.current
    var afterPermission by remember { mutableStateOf<(() -> Unit)?>(null) }
    val askLocalNetwork = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        afterPermission?.invoke()
        afterPermission = null
    }
    val withLocalNetwork = { action: () -> Unit ->
        if (context.needsLocalNetworkPermission(uiState.address)) {
            afterPermission = action
            askLocalNetwork.launch(ACCESS_LOCAL_NETWORK)
        } else {
            action()
        }
    }
    ChooseServerScreen(
        address = address,
        uiState = uiState,
        onLookUp = { withLocalNetwork(viewModel::lookUp) },
        onRetry = { withLocalNetwork(viewModel::retry) },
        onRead = { ServerAddress.parse(uiState.address)?.let(onRead) },
        onUp = onUp,
    )
}

/**
 * The server screen: a field for the address of a server, "Look up", and the instance it found, with [onRead] to read
 * it without an account, or why it found none. It holds no state: [address] is the field's text and [uiState] the rest.
 */
@Composable
fun ChooseServerScreen(
    address: TextFieldState,
    uiState: ChooseServerUiState,
    onLookUp: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onRead: () -> Unit = {},
    onUp: (() -> Unit)? = null,
) {
    // A lookup that starts, or an address found invalid, ends the field's focus, which hides the keyboard, so that the
    // result or the error shows.
    val focusManager = LocalFocusManager.current
    val answered = uiState.lookup is ServerLookupState.Loading || uiState.lookup == ServerLookupState.InvalidAddress
    LaunchedEffect(answered) {
        if (answered) focusManager.clearFocus()
    }
    // On a short window, such as a phone in landscape with the keyboard open, the top app bar scrolls with the form and
    // "Look up" sits beside the field, so that the field and the action fit above the keyboard.
    val compactHeight = !currentWindowAdaptiveInfoV2().windowSizeClass
        .isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)
    Scaffold(
        modifier = modifier,
        topBar = { if (!compactHeight) ChooseServerTopBar(onUp) },
    ) { padding ->
        // The form scrolls above the keyboard, so that "Look up" and the result stay reachable while typing.
        Column(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (compactHeight) ChooseServerTopBar(onUp, windowInsets = WindowInsets(0))
            Column(
                modifier = Modifier.widthIn(max = ContentMaxWidth).fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (compactHeight) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AddressField(address, uiState, onLookUp, Modifier.weight(1f))
                        LookUpButton(uiState, onLookUp)
                    }
                } else {
                    AddressField(address, uiState, onLookUp, Modifier.fillMaxWidth())
                    LookUpButton(uiState, onLookUp, Modifier.align(Alignment.End))
                }
                Box(Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite }) {
                    LookupResult(uiState.lookup, onRetry, onRead)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChooseServerTopBar(onUp: (() -> Unit)?, windowInsets: WindowInsets = TopAppBarDefaults.windowInsets) {
    TopAppBar(
        title = { Text(stringResource(R.string.choose_server_title)) },
        navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
        windowInsets = windowInsets,
    )
}

@Composable
private fun LookUpButton(uiState: ChooseServerUiState, onLookUp: () -> Unit, modifier: Modifier = Modifier) {
    val lookUp = stringResource(R.string.choose_server_look_up)
    Button(
        onClick = onLookUp,
        enabled = uiState.canLookUp,
        modifier = modifier.buttonSemantics(lookUp, uiState.canLookUp, onLookUp),
    ) {
        Text(lookUp)
    }
}

@Composable
private fun AddressField(
    address: TextFieldState,
    uiState: ChooseServerUiState,
    onLookUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val invalid = uiState.lookup == ServerLookupState.InvalidAddress
    OutlinedTextField(
        state = address,
        modifier = modifier,
        label = { Text(stringResource(R.string.choose_server_address_label)) },
        placeholder = { Text(stringResource(R.string.choose_server_address_hint)) },
        supportingText = if (invalid) {
            { Text(stringResource(R.string.choose_server_invalid_address)) }
        } else {
            null
        },
        isError = invalid,
        // An address reads left to right in every language.
        textStyle = LocalTextStyle.current.copy(textDirection = TextDirection.Ltr),
        lineLimits = TextFieldLineLimits.SingleLine,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrectEnabled = false,
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Go,
        ),
        onKeyboardAction = { if (uiState.canLookUp) onLookUp() },
    )
}

@Composable
private fun LookupResult(lookup: ServerLookupState, onRetry: () -> Unit, onRead: () -> Unit) {
    when (lookup) {
        ServerLookupState.Idle, ServerLookupState.InvalidAddress -> Unit

        is ServerLookupState.Loading -> Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularProgressIndicator(Modifier.size(24.dp))
            Text(
                stringResource(R.string.choose_server_looking_up, lookup.host.isolated()),
                style = MaterialTheme.typography.bodyLarge.copy(textDirection = SentenceDirection),
            )
        }

        is ServerLookupState.Found -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = lookup.instance.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.semantics { heading() },
            )
            lookup.instance.shortDescription?.let { Text(it, style = MaterialTheme.typography.bodyLarge) }
            Text(
                text = lookup.host,
                style = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.Ltr),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val read = stringResource(R.string.choose_server_read)
            Button(
                onClick = onRead,
                modifier = Modifier.buttonSemantics(read, enabled = true, onClick = onRead),
            ) {
                Text(read)
            }
        }

        is ServerLookupState.Failed -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = lookup.failure.message(lookup.host.isolated()),
                style = MaterialTheme.typography.bodyLarge.copy(textDirection = SentenceDirection),
                color = MaterialTheme.colorScheme.error,
            )
            val tryAgain = stringResource(R.string.choose_server_try_again)
            OutlinedButton(
                onClick = onRetry,
                modifier = Modifier.buttonSemantics(tryAgain, enabled = true, onClick = onRetry),
            ) {
                Text(tryAgain)
            }
        }
    }
}

/** The one message of a failed lookup of [host], which says what happened. */
@Composable
private fun InstanceLookupFailure.message(host: String): String = when (this) {
    InstanceLookupFailure.NoAnswer -> stringResource(R.string.choose_server_no_answer, host)
    InstanceLookupFailure.NotMycroblog -> stringResource(R.string.choose_server_not_mycroblog, host)
    is InstanceLookupFailure.Problem -> stringResource(R.string.choose_server_problem, host, title)
}

/**
 * The host as a left-to-right isolate (LRI ... PDI) in the sentence it is put in: it reads left to right in any
 * language, and neither moves the sentence's punctuation nor decides the sentence's direction.
 */
private fun String.isolated(): String = "\u2066$this\u2069"

/** A sentence takes the direction of its own words, whatever the layout's direction. */
private val SentenceDirection = TextDirection.Content

/**
 * One accessibility node for a button with a text [label]: its role, label, state and action on the button itself,
 * where the button's text would otherwise be a child node and the button a nameless view.
 */
private fun Modifier.buttonSemantics(label: String, enabled: Boolean, onClick: () -> Unit): Modifier =
    clearAndSetSemantics {
        role = Role.Button
        text = AnnotatedString(label)
        if (!enabled) disabled()
        onClick {
            onClick()
            true
        }
    }

/** The widest the form grows, so that its line stays readable in a wide window. */
private val ContentMaxWidth = 600.dp
