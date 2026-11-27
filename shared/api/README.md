# The client API of my

`openapi.yaml` is the bundled client API document of [mycroblog/my](https://github.com/mycroblog/my)
(`api/openapi.yaml`), taken from the commit in `my-commit` ([ADR-0003](../../docs/adr/0003-api-client-generation.md)
§1). It is licensed under the MIT License in [LICENSE](LICENSE) (my ADR-0093), not under this repository's license.

Never edit it here: change the API in my, then import it and regenerate both clients in one pull request.

```bash
scripts/api-import.sh ../my <commit or tag>
```

```bash
task api:generate
```

`task check` fails when a generated client differs from what `task api:generate` makes from this document.
