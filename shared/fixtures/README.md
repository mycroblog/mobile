# Test fixtures

The expected data that both apps must agree on ([ADR-0002](../../docs/adr/0002-platforms-and-layout.md) §2). A
directory of an answer of my holds one case: `response.json` is what my answers, in the shape of
[the API document](../api/openapi.yaml), and `expected.json` is what a client takes from it. A directory of a rule of
the clients holds `cases.json`, each input with what a client makes of it. The unit tests of both apps read the same files, so changing one
fails both.

- `instance/`: `GET /api/v1/instance`, and the name and short description a client shows of it.
- `server-address/cases.json`: what a person types as a server address, and the origin a client fetches from and the
  host it shows, or `null` for both when the address is invalid.
