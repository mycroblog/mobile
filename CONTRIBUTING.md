# Contributing

## Set up a Mac

The checks run on macOS on Apple silicon. Each step below is what `task check` asks for when it is missing.

1. Install Xcode at the version in [.xcode-version](.xcode-version), the latest released Xcode
   ([ADR-0009](docs/adr/0009-ios-ui-structure.md) §7), from the App Store or
   [developer.apple.com](https://developer.apple.com/download/applications/). Open it once to install its
   components, then select it:

   ```bash
   sudo xcode-select -s /Applications/Xcode.app
   ```

2. Install [Homebrew](https://brew.sh), then [asdf](https://asdf-vm.com), the GitHub CLI and jq:

   ```bash
   brew install asdf gh jq
   ```

   Put asdf's shims first on `PATH`, as [asdf's guide](https://asdf-vm.com/guide/getting-started.html) says for
   your shell, so that the versions of [.tool-versions](.tool-versions) win over other installs.

3. Install the tools of [.tool-versions](.tool-versions) from the repository's root:

   ```bash
   cut -d' ' -f1 .tool-versions | xargs -n1 asdf plugin add
   ```

   ```bash
   asdf install
   ```

4. Sign in to GitHub for `scripts/kit`:

   ```bash
   gh auth login
   ```

## Run the checks

```bash
task check
```

It runs every check, each area in parallel, and names each one that fails with what to do. `task --list` lists the
checks of each area, such as `task docs:check`. `scripts/kit check` runs the same checks.
