package social.mycroblog.api.models

import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * What the account's current moderation restrictions let it post. The
 * server applies the same policy when it posts: a status asked for with a visibility that is
 * not in `visibilities` gets `visibility_ceiling` instead.
 */
@Serializable
public data class PostingLimits(
  /**
   * Who may see a status: `public` everyone, listed on public timelines;
   * `local` logged-in users of this instance and followers; `unlisted` everyone, kept off
   * public timelines; `private` followers and mentioned accounts; `direct` mentioned
   * accounts.
   */
  @SerialName("visibility_ceiling")
  public val visibilityCeiling: Visibility,
  /**
   * The visibilities that a new status keeps as asked for, from the widest audience to the
   * narrowest; every visibility while the account is not restricted. A one-person server
   * (`Instance.single_user`) leaves out `local`, which a new status cannot have there.
   */
  @SerialName("visibilities")
  public val visibilities: List<Visibility>,
)
