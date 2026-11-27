package dev.mycroblog.feature.settings.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.mycroblog.core.designsystem.NavigateUpButton
import dev.mycroblog.core.model.InstanceLookupFailure

/** The server screen with its ViewModel: the address field's text is saved with the screen and sent to the ViewModel. */
@Composable
internal fun ChooseServerRoute(onUp: (() -> Unit)?, viewModel: ChooseServerViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val address = rememberTextFieldState()
    LaunchedEffect(address) {
        snapshotFlow { address.text.toString() }.collect(viewModel::onAddressChange)
    }
    ChooseServerScreen(
        address = address,
        uiState = uiState,
        onLookUp = viewModel::lookUp,
        onRetry = viewModel::retry,
        onUp = onUp,
    )
}

/**
 * The server screen: a field for the address of a server, "Look up", and the instance it found or why it found none.
 * It holds no state: [address] is the field's text and [uiState] the rest.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseServerScreen(
    address: TextFieldState,
    uiState: ChooseServerUiState,
    onLookUp: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    onUp: (() -> Unit)? = null,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.choose_server_title)) },
                navigationIcon = { if (onUp != null) NavigateUpButton(onClick = onUp) },
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier.padding(padding).verticalScroll(rememberScrollState()).fillMaxWidth(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier.widthIn(max = ContentMaxWidth).fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                AddressField(address, uiState, onLookUp)
                Button(onClick = onLookUp, enabled = uiState.canLookUp, modifier = Modifier.align(Alignment.End)) {
                    Text(stringResource(R.string.choose_server_look_up))
                }
                Box(Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite }) {
                    LookupResult(uiState.lookup, onRetry)
                }
            }
        }
    }
}

@Composable
private fun AddressField(address: TextFieldState, uiState: ChooseServerUiState, onLookUp: () -> Unit) {
    val invalid = uiState.lookup == ServerLookupState.InvalidAddress
    OutlinedTextField(
        state = address,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.choose_server_address_label)) },
        placeholder = { Text(stringResource(R.string.choose_server_address_hint)) },
        supportingText = if (invalid) {
            { Text(stringResource(R.string.choose_server_invalid_address)) }
        } else {
            null
        },
        isError = invalid,
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
private fun LookupResult(lookup: ServerLookupState, onRetry: () -> Unit) {
    when (lookup) {
        ServerLookupState.Idle, ServerLookupState.InvalidAddress -> Unit

        is ServerLookupState.Loading -> Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularProgressIndicator(Modifier.size(24.dp))
            Text(
                stringResource(R.string.choose_server_looking_up, lookup.host),
                style = MaterialTheme.typography.bodyLarge,
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
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        is ServerLookupState.Failed -> Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.choose_server_could_not_reach, lookup.host),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
            )
            Text(lookup.failure.reason(), style = MaterialTheme.typography.bodyMedium)
            OutlinedButton(onClick = onRetry) {
                Text(stringResource(R.string.choose_server_try_again))
            }
        }
    }
}

@Composable
private fun InstanceLookupFailure.reason(): String = when (this) {
    InstanceLookupFailure.NoAnswer -> stringResource(R.string.choose_server_no_answer)
    InstanceLookupFailure.NotMycroblog -> stringResource(R.string.choose_server_not_mycroblog)
    is InstanceLookupFailure.Problem -> title
}

/** The widest the form grows, so that its line stays readable in a wide window. */
private val ContentMaxWidth = 600.dp
