package dev.mycroblog.core.api.models

import kotlin.Boolean
import kotlin.Int
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Who the instance setting `login_providers_only` keeps from signing in, whether or not the setting
 * is on. Sessions and tokens keep working; these users cannot sign in again once they end.
 */
@Serializable
public data class LoginProvidersOnlyImpact(
  /**
   * The value of the setting. The limit applies while it is on and `enabled_providers` is not 0.
   */
  @SerialName("setting_on")
  public val settingOn: Boolean,
  /**
   * How many login providers users can sign in with now. With none, the limit does not apply.
   */
  @SerialName("enabled_providers")
  public val enabledProviders: Int,
  /**
   * How many active local users have no link to one of those providers, or to the provider
   * `provider_id` named. With no provider at all, it is every active local user.
   */
  @SerialName("users_without_provider")
  public val usersWithoutProvider: Int,
  /**
   * Whether the user who asks is one of them.
   */
  @SerialName("includes_viewer")
  public val includesViewer: Boolean,
  /**
   * Whether adding a provider, or enabling the provider `provider_id` named, would start the limit
   * at once: the setting is on, no provider can be signed in with now, and that one could be.
   */
  @SerialName("starts_limit")
  public val startsLimit: Boolean,
)
