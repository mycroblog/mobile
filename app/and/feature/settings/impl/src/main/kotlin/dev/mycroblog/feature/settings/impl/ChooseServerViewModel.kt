package dev.mycroblog.feature.settings.impl

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.mycroblog.core.data.InstanceRepository
import dev.mycroblog.core.model.InstanceLookup
import dev.mycroblog.core.model.ServerAddress
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * The state of the server screen: it looks up the address the user typed and holds the result.
 *
 * The address and whether it was found invalid are kept in [savedStateHandle], so that the error comes back after
 * the process ends. A lookup's result is not: coming back, the screen shows the address with no result, and the user
 * looks it up again.
 */
@HiltViewModel
class ChooseServerViewModel @Inject constructor(
    private val repository: InstanceRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val state = MutableStateFlow(
        ChooseServerUiState(
            address = savedStateHandle[ADDRESS] ?: "",
            lookup = if (savedStateHandle.get<Boolean>(INVALID) == true) {
                ServerLookupState.InvalidAddress
            } else {
                ServerLookupState.Idle
            },
        ),
    )
    val uiState: StateFlow<ChooseServerUiState> = state.asStateFlow()

    private var lookup: Job? = null

    /** The address field now holds [text]. A change clears the result, and stops a lookup under way. */
    fun onAddressChange(text: String) {
        if (text == state.value.address) return
        lookup?.cancel()
        state.value = ChooseServerUiState(address = text)
        savedStateHandle[ADDRESS] = text
        savedStateHandle[INVALID] = false
    }

    /** Looks up the address in the field, or says that it is not an address. */
    fun lookUp() {
        val current = state.value
        if (!current.canLookUp) return
        val address = ServerAddress.parse(current.address)
        if (address == null) {
            state.value = current.copy(lookup = ServerLookupState.InvalidAddress)
            savedStateHandle[INVALID] = true
            return
        }
        start(address)
    }

    /** Repeats the lookup that failed, of the same address. */
    fun retry() {
        val failed = state.value.lookup as? ServerLookupState.Failed ?: return
        ServerAddress.parse(state.value.address)?.takeIf { it.host == failed.host }?.let(::start)
    }

    private fun start(address: ServerAddress) {
        lookup?.cancel()
        state.update { it.copy(lookup = ServerLookupState.Loading(address.host)) }
        lookup = viewModelScope.launch {
            val lookup = when (val result = repository.lookUp(address)) {
                is InstanceLookup.Found -> ServerLookupState.Found(address.host, result.instance)
                is InstanceLookup.Failed -> ServerLookupState.Failed(address.host, result.failure)
            }
            state.update { it.copy(lookup = lookup) }
        }
    }

    private companion object {
        const val ADDRESS = "address"
        const val INVALID = "invalid"
    }
}
