package dev.mycroblog.core.model

import java.net.URI
import java.net.URISyntaxException

/**
 * The address of a server: its scheme, host and port, and nothing else. It is https, or http only for the local my
 * that the tests run ([LOCAL_HOSTS]).
 */
@ConsistentCopyVisibility
data class ServerAddress private constructor(
    /** `https`, or `http` for a host of [LOCAL_HOSTS]. */
    val scheme: String,
    /** The host, in lower case, such as `example.social`. */
    val host: String,
    /** The port, or null for the scheme's default. */
    val port: Int?,
) {
    /** The origin the requests go to, such as `https://example.social` or `http://10.0.2.2:8790`. */
    val origin: String
        get() = if (port == null) "$scheme://$host" else "$scheme://$host:$port"

    companion object {
        /** The hosts that may be reached over http: the local my, from the JVM and from the emulator. */
        val LOCAL_HOSTS: Set<String> = setOf("localhost", "127.0.0.1", "10.0.2.2")

        private const val MAX_PORT = 65535

        /**
         * The address that the user typed as [text], or null when it is not one. Spaces around it are trimmed, https
         * is used when it names no scheme, and a path, a query or a fragment is dropped.
         */
        fun parse(text: String): ServerAddress? {
            val trimmed = text.trim()
            if (trimmed.isEmpty()) return null
            val withScheme = if ("://" in trimmed) trimmed else "https://$trimmed"
            val uri = try {
                URI(withScheme)
            } catch (_: URISyntaxException) {
                return null
            }
            val scheme = uri.scheme?.lowercase() ?: return null
            val host = uri.host?.lowercase()?.takeIf { it.isNotEmpty() } ?: return null
            if (uri.rawUserInfo != null) return null
            val allowed = scheme == "https" || (scheme == "http" && host in LOCAL_HOSTS)
            if (!allowed) return null
            val port = uri.port.takeIf { it != -1 }
            if (port != null && port !in 1..MAX_PORT) return null
            return ServerAddress(scheme, host, port)
        }
    }
}
