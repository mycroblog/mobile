# The app-driving tool

`scripts/drive <ios|android> <step>` drives an app on the iOS Simulator or the Android Emulator one step at a time
(ADR-0011 §3); `scripts/drive --help` lists the steps. It is made of:

- the client, `scripts/drive` and this folder's `*.mjs` (Node), which runs `simctl`, `devicectl`, `idb` and `adb` for
  the changes of the device, and talks to the runner for the screen. `scripts/kit check` runs its tests
  (`node --test tools/drive/`).
- a runner per OS, a UI test that never ends and answers the protocol below: [`ios/`](ios/) (XCUITest) and
  [`android/`](android/) (UI Automator).

The iPhone Duo hinge needs idb (MIT), which no public Apple tool replaces: `brew install facebook/fb/idb-companion`,
then `pipx install fb-idb`. A session's state, logs and screenshots are under `$DRIVE_STATE_DIR`, by default `$TMPDIR/mycroblog-drive/`. The
tool creates its own devices (`drive-<kind>`) and never drives one it did not create.

## Runner protocol

The client talks to the runner over TCP on 127.0.0.1, one JSON object per line in each direction, one reply per
request. A request is `{"op": "<op>", "app": "<bundle id or package of the app under test>", ...arguments}`.

| op | arguments | what |
| --- | --- | --- |
| `ping`, `quit` | | reply `{"ok": true}` only; `quit` then ends the runner |
| `look` | | nothing; the screen |
| `tap` | `label`, `id`, or `x` + `y` | tap the element (searched in the app, then in the system UI over it) or the point |
| `type` | `text`; optional `label` or `id` of a field | type into the named or the focused field, spaces kept |
| `scroll` | `direction`: `up` `down` `left` `right`; optional `label` or `id` of a container | bring the content in that direction into view |
| `back` | | Android: the system back. iOS: the navigation bar's back button, else a swipe from the left edge |
| `home` | | press home |
| `launch` (iOS) | `args`: launch arguments | (re)launch the app |
| `activate` (iOS) | | bring the app to the foreground |
| `orientation` (iOS) | `value`: `portrait` `landscape-left` `landscape-right` `upside-down` | |
| `open` (iOS) | `url` | open it as the system does, without a confirmation alert |
| `share` (iOS) | `url` (or text), `target`: an app's name in the share sheet | the runner's host app opens the system share sheet for it; pick `target` |
| `resize` (iOS) | `width`, `height` in points | drag an iPadOS window's resize grabber until the window is that size, as near as it goes |

A reply is `{"ok": true|false, "error": "<one sentence>", "tree": <node>, "screenshot": "<base64 PNG>"}`.

- Every op but `ping` and `quit` returns the tree and the screenshot, also when it fails, so the client can say what
  is on the screen instead. An op that acts on the app fails with `<app> is not running` when its process is not
  running, and an element that is not there with `no element with label "X"` (or id) after waiting 2 s.
- The reply waits until the screen settles: two reads of the tree 100 ms apart are equal (for at most 3 s). After a
  gesture on Android, they must also differ from the tree before it, or 1 s must have passed: idle came before a
  new screen had drawn.
- A node is `{"role", "label", "id", "value", "frame": [x, y, w, h], "states": [...], "children": [...]}`, empty fields
  left out. `role` is the platform's type name (iOS: the `XCUIElement.ElementType` name; Android: the role
  description, else the class name). `states` holds `selected`, `disabled`, `focused`, `checked` and, on Android,
  `heading`: XCUITest has no public API for traits. A node with no label, id, value or state is folded into its parent.
- The root is `Screen`. On iOS its children are the apps in front, with springboard when it shows an alert, a banner,
  or the home screen. On Android they are the windows, each a `Window` node titled by its window, top-most first.
- `frame` and `x`/`y` are screen coordinates: iOS points (the screenshot is scaled to points, so its pixels are the
  same), Android pixels. Ids on Android are full resource names; an `id` argument may leave out the `<package>:id/`.
