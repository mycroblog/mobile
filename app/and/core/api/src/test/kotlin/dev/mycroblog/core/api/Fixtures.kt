package dev.mycroblog.core.api

import java.io.File

/**
 * The shared fixtures of shared/fixtures (ADR-0002 §2), read from the repository: the test task passes their directory
 * in the system property `mycroblog.fixtures`.
 */
object Fixtures {
    private val directory: File by lazy {
        val path = System.getProperty("mycroblog.fixtures")
        check(!path.isNullOrEmpty()) {
            "the system property mycroblog.fixtures is not set: run the tests through Gradle (task android:test)"
        }
        File(path)
    }

    /** The text of the fixture at [path] under shared/fixtures, such as `instance/response.json`. */
    fun read(path: String): String {
        val file = directory.resolve(path)
        check(file.isFile) { "there is no fixture shared/fixtures/$path (${file.absolutePath})" }
        return file.readText()
    }
}
