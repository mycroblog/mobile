# Test fixtures

The expected data that both apps must agree on ([ADR-0002](../../docs/adr/0002-platforms-and-layout.md) §2). Each
directory holds one case: `response.json` is what my answers, in the shape of [the API document](../api/openapi.yaml),
and `expected.json` is what a client takes from it. The unit tests of both apps read the same files, so changing one
fails both.

- `instance/`: `GET /api/v1/instance`, and the name and short description a client shows of it.
