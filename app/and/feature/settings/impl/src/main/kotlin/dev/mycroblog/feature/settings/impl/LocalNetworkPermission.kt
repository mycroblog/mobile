package dev.mycroblog.feature.settings.impl

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import dev.mycroblog.core.model.ServerAddress
import java.net.InetAddress

/**
 * The permission that an app targeting Android 17 needs to reach an address of the local network, such as the
 * emulator's 10.0.2.2. Only the debug build declares it, for the local my; loopback does not need it.
 */
internal const val ACCESS_LOCAL_NETWORK = "android.permission.ACCESS_LOCAL_NETWORK"

private const val ANDROID_17 = 37

private val Ipv4Literal = Regex("""^\d{1,3}(\.\d{1,3}){3}$""")

/**
 * Whether looking up [text] must first ask for [ACCESS_LOCAL_NETWORK]: it names a private or link-local IPv4 address,
 * and the app declares the permission and does not hold it yet.
 */
internal fun Context.needsLocalNetworkPermission(text: String): Boolean {
    if (Build.VERSION.SDK_INT < ANDROID_17) return false
    val host = ServerAddress.parse(text)?.host ?: return false
    // A numeric host is read without a lookup of the name.
    if (!Ipv4Literal.matches(host)) return false
    val address = InetAddress.getByName(host)
    if (!address.isSiteLocalAddress && !address.isLinkLocalAddress) return false
    if (checkSelfPermission(ACCESS_LOCAL_NETWORK) == PackageManager.PERMISSION_GRANTED) return false
    val requested = packageManager
        .getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(PackageManager.GET_PERMISSIONS.toLong()))
        .requestedPermissions
        .orEmpty()
    return ACCESS_LOCAL_NETWORK in requested
}
