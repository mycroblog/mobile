# swift-developer: decisions

- Use the generated client only inside the API package: its types are `package` in the target GeneratedAPI; expose hand-written public types from the target API, never a generated one - ADR-0009 §7 keeps the generated types inside the package (#8, 2026-11-27)
- Never edit app/ios/Packages/API/Sources/GeneratedAPI or shared/api/openapi.yaml by hand; change openapi-generator-config.yaml and run `task api:generate` - `task ios:api-check` refuses any other difference (#8, 2026-11-27)
- Present a sheet from RootView, above the tab container, and restore it from @SceneStorage values; a destination is rebuilt when the width changes the tab set, which closes a sheet it owns - the ios-evaluator found the server sheet closing on iPad when widened (#11, 2026-11-27)
