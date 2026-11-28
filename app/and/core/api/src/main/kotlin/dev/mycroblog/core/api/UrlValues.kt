package dev.mycroblog.core.api

import io.ktor.http.encodeURLParameter

/*
 * The generated clients put the values of path and query parameters into the URL as they are (ADR-0003 §2), so a
 * value with a reserved character would reach the server as another value: `+` as a space, `&` as the start of another
 * parameter, `/` as another path segment. Every string the app passes as a path or a query value goes through one of
 * these first, once; the generated client then sends it byte for byte.
 */

/** [value] percent-encoded as one path segment: every byte but the unreserved characters of RFC 3986, `/` included. */
public fun pathValue(value: String): String = value.encodeURLParameter()

/** [value] percent-encoded as a query value: every byte but the unreserved characters of RFC 3986; a space is `%20`. */
public fun queryValue(value: String): String = value.encodeURLParameter()
