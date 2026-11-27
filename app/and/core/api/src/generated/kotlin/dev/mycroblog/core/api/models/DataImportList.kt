package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The user's latest imports, newest first.
 */
@Serializable
public data class DataImportList(
  @SerialName("items")
  public val items: List<DataImport>,
)
