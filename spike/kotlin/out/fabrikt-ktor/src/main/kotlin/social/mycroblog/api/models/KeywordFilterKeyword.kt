package social.mycroblog.api.models

import kotlin.Boolean
import kotlin.String
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A word of a keyword filter.
 */
@Serializable
public data class KeywordFilterKeyword(
  /**
   * The word or phrase, as the user typed it. It matches the text, the content warning, the
   * poll's choices, and the alt text of a post, each on its own, regardless of case, of
   * half-width and full-width forms, and of katakana and hiragana; runs of white space match
   * one space. Up to 100 characters once normalized.
   */
  @SerialName("keyword")
  public val keyword: String,
  /**
   * Whether the word matches only where no letter, digit, or `_` touches it. Characters of
   * scripts that do not separate words with spaces (Chinese, Japanese, Thai, and the like)
   * never count as touching, so there the word matches anywhere.
   */
  @SerialName("whole_word")
  public val wholeWord: Boolean,
)
