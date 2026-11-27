# spike/app-driving/ios (mycroblog/mobile#15, throwaway)

- `xcui-start.sh` / `xcui.py`: the XCUITest driver server (`spike/Spike3/Spike3UITests/DriverServer.swift`), one JSON command per line on TCP.
- `bench_axe.py`, `bench_idb.sh`, `mcp_client.py`: one step (tap + tree + screenshot) timed through AXe, idb and `maestro mcp`.
- `axe-tree.py`: AXe `describe-ui` JSON to indented lines.
- `samples/`: one tree of the Home screen from each tool.
