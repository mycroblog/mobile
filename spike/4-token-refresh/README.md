# Spike #4: serialized token refresh across the iOS app and its Share Extension

Throwaway code. An app and a Share Extension share one App Group (`group.org.mycroblog.spike4`, also used as the
Keychain access group) and refresh one token against `server.js`, a fake token server that rotates the refresh
token on every use and revokes the whole family when a used one comes back (like `my`).

- `Shared/Refresher.swift`: the four variants (`none`, `flock`, `coord` for NSFileCoordinator, `lease`).
- `App/App.swift`: launch arguments `-mode`, `-n`, `-pause`, `-reset 1`, `-autorun 1`, `-startIn <s>`.
- `ShareExt/`: runs the same loop when the share sheet opens it, with the configuration the app last wrote.
- `evidence/`: client logs (app and extension in one file) and server logs of each run.

Run: `node server.js`, build the `Spike4` scheme for the iPhone 18 Pro simulator (signing identity `-`, no team), launch
with arguments via `xcrun simctl launch`, open the share sheet in the app or in Safari and pick Spike4. The server
latency is set with `POST /config?delay=<ms>`.

Known shortcut: the lease variant takes the lease before it begins the background task; a real implementation begins
the background task (or expiring activity) first, so that no file lock, however short, is taken by a process that may
be suspended.
