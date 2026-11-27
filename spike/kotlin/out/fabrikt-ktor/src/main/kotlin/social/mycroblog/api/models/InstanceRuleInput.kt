package social.mycroblog.api.models

import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The text and the hint of a rule, written once in any language and shown as they are in
 * every locale; they replace the earlier ones.
 */
@Serializable
public data class InstanceRuleInput(
  /**
   * The rule, on one line, as plain text.
   */
  @SerialName("text")
  public val text: String,
  /**
   * An explanation of the rule, as plain text; omitted or empty for none.
   */
  @SerialName("hint")
  public val hint: String? = null,
)
