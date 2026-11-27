# kotlin-developer: decisions

- Never edit `app/and/core/api/src/generated/kotlin`; change the document in my, import it, and run `task api:generate`. Hand-written API code goes in `core/api/src/main/kotlin` - `task android:api-check` compares the whole generated directory (#8, 2026-11-27)
- Give the generated clients an `HttpClient` set up with `installApi(origin)` and read an error body with `NetworkError.Http.problem()`; without `ApiJson` the `@Contextual` UUID and URI properties do not decode (#8, 2026-11-27)
