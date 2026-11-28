# Test fixtures

The expected data that both apps must agree on ([ADR-0002](../../docs/adr/0002-platforms-and-layout.md) §2). A
directory of an answer of my holds one case: `response.json` is what my answers, in the shape of
[the API document](../api/openapi.yaml), and `expected.json` is what a client takes from it. A directory of a rule of
the clients holds `cases.json`, each input with what a client makes of it. The unit tests of both apps read the same files, so changing one
fails both.

- `instance/`: `GET /api/v1/instance`, and the name and short description a client shows of it.
- `server-address/cases.json`: what a person types as a server address, and the origin a client fetches from and the
  host it shows, or `null` for both when the address is invalid.
- `post-content/cases.json`: the HTML of a post's content, and the plain text of a content warning or a display
  name, with its custom emoji, and the blocks of styled and linked runs a client shows of it.
- `timeline-page/`: a page of `GET /api/v1/timelines/local`, and what a client shows of each post: the author,
  who boosted it, the content warning, what is hidden, the media, and the counts. An empty display name shows
  the username, or the domain when that is empty too; the handle is `@` and `acct`, or none when `acct` is empty; the
  avatar is `avatar.small`. A boost shows the boosted post, with its booster as `boosted_by`. A content warning hides
  the content and the media; `sensitive` hides the media.
- `public-timelines/cases.json`: the `timelines` and `single_user` of an instance, and the server-provided timelines
  a client offers a reader who has not signed in.
