package social.mycroblog.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Who may read the public timelines the instance serves: `local` is the instance
 * setting `local_timeline_visibility` of `GET /timelines/local`, and `federated` the instance
 * setting `federated_timeline_visibility` of `GET /timelines/federated`. Clients do not offer a
 * timeline that is `off`, nor one that is `signed_in` to a visitor who is not logged in. On a
 * one-person server (`single_user`) both are `off`, whatever the settings store.
 */
@Serializable
public data class InstanceTimelines(
  /**
   * Who may read something public about the instance, the value of the instance settings
   * `local_timeline_visibility`, `federated_timeline_visibility`, `domain_actions_visibility`,
   * `domain_actions_rationale_visibility`, and `supporter_list_visibility`: `off`, nobody, and the
   * resource is a 404; `signed_in`, the users signed in to this server, and an anonymous request is
   * a 401; `everyone`, everyone, anonymous visitors included.
   */
  @SerialName("local")
  public val local: InstanceAudience,
  /**
   * Who may read something public about the instance, the value of the instance settings
   * `local_timeline_visibility`, `federated_timeline_visibility`, `domain_actions_visibility`,
   * `domain_actions_rationale_visibility`, and `supporter_list_visibility`: `off`, nobody, and the
   * resource is a 404; `signed_in`, the users signed in to this server, and an anonymous request is
   * a 401; `everyone`, everyone, anonymous visitors included.
   */
  @SerialName("federated")
  public val federated: InstanceAudience,
)
