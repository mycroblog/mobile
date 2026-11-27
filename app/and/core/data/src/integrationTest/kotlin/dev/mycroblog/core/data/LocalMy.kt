package dev.mycroblog.core.data

/** The local my that the integration tests talk to: task android:test starts it (task my:up) and passes its URL. */
object LocalMy {
    /** Its origin, such as `http://localhost:8790`. */
    val url: String by lazy {
        val url = System.getProperty("mycroblog.my.url")
        check(!url.isNullOrEmpty()) {
            "MY_URL is not set: run the integration tests with task android:test, which starts the local my, " +
                "or start it with task my:up and set MY_URL to the URL it prints"
        }
        url
    }
}
