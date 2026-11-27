# kotlin-developer: decisions

- Never edit `app/and/core/api/src/generated/kotlin`; change the document in my, import it, and run `task api:generate`. Hand-written API code goes in `core/api/src/main/kotlin` - `task android:api-check` compares the whole generated directory (#8, 2026-11-27)
- Give the generated clients an `HttpClient` set up with `installApi(origin)` and read an error body with `NetworkError.Http.problem()`; without `ApiJson` the `@Contextual` UUID and URI properties do not decode (#8, 2026-11-27)
- Run `task android:format` before committing Kotlin; `task android:check` runs ktlint (style in `.editorconfig`), Android Lint with warnings as errors, and the layout check of ADR-0010 §4 (no orientation, screen or display size: use `currentWindowAdaptiveInfoV2().windowSizeClass`) (#9, 2026-11-27)
- Only `:core:data` depends on `:core:api`, and through `implementation`; the build refuses anything else (#9, 2026-11-27)
