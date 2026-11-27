package dev.mycroblog.feature.settings.impl

import dev.mycroblog.core.model.Instance
import dev.mycroblog.core.model.InstanceLookupFailure

/** What the server screen shows: the address the field holds, and where its lookup is. */
data class ChooseServerUiState(
    /** The text of the address field. */
    val address: String = "",
    val lookup: ServerLookupState = ServerLookupState.Idle,
) {
    /** Whether "Look up" may be used: the field holds something, and no lookup is under way. */
    val canLookUp: Boolean
        get() = address.isNotEmpty() && lookup !is ServerLookupState.Loading
}

/** Where the lookup of a server is. */
sealed interface ServerLookupState {
    /** Nothing was looked up, or the address changed after the last result. */
    data object Idle : ServerLookupState

    /** The address is not one a server can have; nothing was fetched. */
    data object InvalidAddress : ServerLookupState

    /** The instance of [host] is being fetched. */
    data class Loading(val host: String) : ServerLookupState

    /** [host] is a mycroblog server, whose instance is [instance]. */
    data class Found(val host: String, val instance: Instance) : ServerLookupState

    /** The instance of [host] could not be read, for [failure]; "Try again" repeats the lookup. */
    data class Failed(val host: String, val failure: InstanceLookupFailure) : ServerLookupState
}
