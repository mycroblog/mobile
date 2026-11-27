package dev.mycroblog.core.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Samples of how fast the web client responded, sent together.
 */
@Serializable
public data class ClientMeasurementReport(
  @SerialName("measurements")
  public val measurements: List<ClientMeasurement>,
)
