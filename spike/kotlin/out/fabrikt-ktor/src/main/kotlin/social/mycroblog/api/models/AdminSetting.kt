package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.collections.List
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An instance setting: its value and what it accepts. Every value is a string in its
 * canonical form, whatever its kind.
 */
@Serializable
public data class AdminSetting(
  /**
   * The key of an instance setting. New keys may be
   * added; clients show the ones they know.
   */
  @SerialName("key")
  public val key: InstanceSettingKey,
  /**
   * The type of a setting's value. `bool`: `true` or `false`. `int`: a decimal integer from
   * `minimum` to `maximum`. `enum`: one of `choices`. `locale`: a BCP 47 language tag.
   * `media`: the ID of an image uploaded with `POST /media`, or empty for none. `account`:
   * the username of a local account, or empty for none. `email`: an email address such as
   * `admin@example.com`, or empty for none. `uri`: an `https:` or `mailto:` URI, or empty for
   * none. `accounts`: the usernames of up to 20 local accounts separated by spaces (commas and
   * a leading `@` are accepted), in order, or empty for none. `client_limits`: comma-separated
   * `client_id=requests` pairs, or empty for none. `provider_list`: comma-separated translation
   * provider IDs, the first tried first, or empty for none. `provider_visibilities`:
   * comma-separated `provider=visibility|visibility` pairs, or empty for none. `decimal`: a
   * decimal number that is not negative in the major unit of a currency (`1650`, `12.34`), or
   * empty for none. `currency`: an ISO 4217 code such as `JPY`, or empty for none.
   */
  @SerialName("kind")
  public val kind: InstanceSettingKind,
  /**
   * The current value, which is `default` when `stored` is false.
   */
  @SerialName("value")
  public val `value`: String,
  /**
   * The value of the setting while it has never been set.
   */
  @SerialName("default")
  public val default: String,
  /**
   * Whether the setting has been set; false means its default applies.
   */
  @SerialName("stored")
  public val stored: Boolean,
  /**
   * The values an `enum` setting accepts, in order; omitted for other kinds.
   */
  @SerialName("choices")
  public val choices: List<String>? = null,
  /**
   * The least value of an `int` setting; omitted for other kinds.
   */
  @SerialName("minimum")
  public val minimum: Int? = null,
  /**
   * The greatest value of an `int` setting; omitted for other kinds.
   */
  @SerialName("maximum")
  public val maximum: Int? = null,
  /**
   * The image of a `media` setting (`preview_image`, `thumbnail`): the large still variant of its
   * media file, as link previews and the about page use it.
   */
  @SerialName("image")
  public val image: AdminSettingImage? = null,
)
