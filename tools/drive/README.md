# The app-driving tool

`scripts/drive <ios|android> <step>` drives an app on the iOS Simulator or the Android Emulator one step at a time
(ADR-0011 §3); `scripts/drive --help` lists the steps. It is made of:

- the client, `scripts/drive` and this folder's `*.mjs` (Node), which runs `simctl`, `devicectl`, `idb` and `adb` for
  the changes of the device, and talks to the runner for the screen. `scripts/kit check` runs its tests
  (`node --test tools/drive/`).
- a runner per OS, a UI test that never ends and answers the protocol below: [`ios/`](ios/) (XCUITest) and
  [`android/`](android/) (UI Automator).

A session's state, logs and screenshots are under `$DRIVE_STATE_DIR`, by default `$TMPDIR/mycroblog-drive/`. The
tool creates its own devices (`drive-<kind>`) and never drives one it did not create.

## Runner protocol

The client talks to the runner over TCP on 127.0.0.1, one JSON object per line in each direction, one reply per
request.

## Request

`{"op": "<op>", "app": "<bundle id or package of the app under test>", ...arguments}`

| op | arguments | what |
| --- | --- | --- |
| `ping` | | reply `{"ok": true}` only, no tree, no screenshot |
| `look` | | nothing; the tree and screenshot after settling |
| `tap` | one of `label`, `id`, or `x` + `y` (points / pixels of the screenshot) | tap the element, searched in the app first, then in system UI (iOS springboard and other apps on screen; Android: any window) |
| `type` | `text`; optional `label` or `id` of a field to focus first | type into the focused or the named field; spaces must be kept |
| `scroll` | `direction`: `up` `down` `left` `right` (the direction the *content* moves into view: `down` shows what is below); optional `label`/`id` of the scroll container | |
| `back` | | Android: pressBack. iOS: the navigation bar's back button, else a left-edge swipe |
| `home` | | press home |
| iOS only `launch` | `args`: list of launch arguments | (re)launch `app` with these arguments (RTL uses this) |
| iOS only `activate` | | bring `app` to the foreground |
| iOS only `orientation` | `value`: `portrait` `landscape-left` `landscape-right` `upside-down` | |
| iOS only `open` | `url` | `XCUIDevice.shared.system.open` (no confirmation alert) |
| iOS only `share` | `url`, `target`: the name of the app in the share sheet | open the URL in Safari, open its share sheet, pick `target` |
| iOS only `resize` | `width`, `height` (points) | iPadOS windowed apps: drag the window's resize grabber until the app's window has this size, as near as it goes |

## Reply

`{"ok": true|false, "error": "<what went wrong, in one sentence>", "tree": <Node>, "screenshot": "<base64 PNG>"}`

- Every op except `ping` returns `tree` and `screenshot`, **also when it fails**, so the client can say what is on the
  screen instead.
- The tree and screenshot are taken only after the screen settles: wait for idle, then read the tree every 100 ms until
  two reads in a row are equal (at most 3 s).
- An op that acts on the app (`tap`, `type`, `scroll`, `back`) fails with `error: "<app> is not running"` when the
  app's process is not running at all.
- An element that is not there fails with `error: "no element with label \"X\""` (or id), after waiting up to 2 s.

## Node

`{"role": "Button", "label": "Compose", "id": "compose", "value": "...", "frame": [x, y, w, h], "states": ["selected", "disabled", "focused", "checked", "heading"], "children": [Node]}`

- `role` is the platform's own element type name (iOS: the `XCUIElement.ElementType` name such as `Button`,
  `StaticText`, `NavigationBar`, `Cell`; Android: the role description when set, else the simple class name such as
  `Button`, `TextView`, `EditText`).
- Leave out empty fields. Fold containers that have no label, no id and no state into their parent (their children
  move up).
- The root is `{"role": "Screen", "children": [...]}`: the app under test when it is in front, and the system UI that
  is over it or in front instead (iOS: springboard alerts, banners, and the app in front; Android: all windows).
- `frame` is in screen coordinates (iOS points, Android pixels), so a `tap` at a frame's centre lands on it. On iPadOS
  windowed apps add the window origin.
