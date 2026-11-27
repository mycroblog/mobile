package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user's latest data exports, newest first.
 */
@Serializable
public data class DataExportList(
  @SerialName("items")
  public val items: List<DataExport>,
)
