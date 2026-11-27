package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The custom emoji of the picker.
 */
@Serializable
public data class PickerCustomEmojiList(
  @SerialName("items")
  public val items: List<PickerCustomEmoji>,
)
