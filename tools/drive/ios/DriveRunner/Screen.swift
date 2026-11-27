// What is on the screen: the accessibility tree of the protocol's Node shape, and a screenshot, read once the screen
// has settled.
import XCTest

final class Screen {
    private unowned let driver: Driver

    init(driver: Driver) {
        self.driver = driver
    }

    /// The point at x and y points of the screen. It is taken from the app in front, whose frame turns with the
    /// screen while springboard's stays in portrait on iPhone; from springboard when the app is in an iPadOS window.
    func point(x: Double, y: Double, _ appID: String?) -> XCUICoordinate {
        var front = fronts(appID).first ?? driver.springboard
        if Windows(driver: driver).origin(of: front) != .zero { front = driver.springboard }
        return front.coordinate(withNormalizedOffset: .zero).withOffset(CGVector(dx: x, dy: y))
    }

    /// The apps in front, the app under test first.
    private func fronts(_ appID: String?) -> [XCUIApplication] {
        if let appID {
            let app = driver.application(appID)
            if app.state == .runningForeground { return [app] }
        }
        return driver.others(than: appID).filter { $0 !== driver.springboard && $0.state == .runningForeground }
    }

    /// Reads the tree every 100 ms until two reads in a row are equal, for at most 3 s, then takes the screenshot.
    func settled(_ appID: String?) -> ([String: Any], String) {
        let deadline = Date().addingTimeInterval(3)
        var tree = read(appID)
        var last = encode(tree)
        while Date() < deadline {
            RunLoop.current.run(until: Date().addingTimeInterval(0.1))
            let next = read(appID)
            let encoded = encode(next)
            tree = next
            if encoded == last { break }
            last = encoded
        }
        return (tree, screenshot())
    }

    private func encode(_ tree: [String: Any]) -> Data {
        (try? JSONSerialization.data(withJSONObject: tree, options: [.sortedKeys])) ?? Data()
    }

    /// The root: the app under test when it is in front, and springboard when the app is not in front or springboard
    /// shows an alert or a banner over it. XCUITest snapshots one app at a time, so each is read on its own.
    func read(_ appID: String?) -> [String: Any] {
        var roots = fronts(appID)
        let springboard = driver.springboard
        if roots.isEmpty || springboard.descendants(matching: .any).matching(Self.overlay).firstMatch.exists {
            roots.append(springboard)
        }
        let windows = Windows(driver: driver)
        var children: [[String: Any]] = []
        for root in roots {
            guard let snapshot = try? root.snapshot() else { continue }
            let offset = root === springboard ? .zero : windows.origin(of: root)
            let bounds = snapshot.frame.offsetBy(dx: offset.x, dy: offset.y)
            children += nodes(snapshot, offset: offset, bounds: bounds)
        }
        var tree: [String: Any] = ["role": "Screen"]
        if !children.isEmpty { tree["children"] = children }
        return tree
    }

    /// What springboard shows over the app in front: an alert or a notification banner.
    private static let overlay = NSPredicate(
        format: "elementType == %lu OR identifier == %@", XCUIElement.ElementType.alert.rawValue,
        "NotificationShortLookView"
    )

    /// The identifiers of the containers of another process's views.
    private static let remoteContainers: Set<String> = ["ShareSheet.RemoteContainerView"]

    private static let foldable: Set<XCUIElement.ElementType> = [.other, .group, .window, .layoutArea, .layoutItem]

    /// The node of a snapshot, or its children when it is a container with no label, no id, no value and no state.
    /// An element wholly off the screen is left out with its children.
    private func nodes(_ snapshot: XCUIElementSnapshot, offset: CGPoint, bounds: CGRect) -> [[String: Any]] {
        let frame = snapshot.frame.offsetBy(dx: offset.x, dy: offset.y)
        if snapshot.elementType != .application, !frame.isEmpty, !bounds.isEmpty, !frame.intersects(bounds) {
            return []
        }
        // In a snapshot, the views another process shows inside a container (the share sheet) have frames relative to
        // it; queried elements have screen frames.
        let inner = Self.remoteContainers.contains(snapshot.identifier) ? CGPoint(x: frame.minX, y: frame.minY) : offset
        let children = snapshot.children.flatMap { nodes($0, offset: inner, bounds: bounds) }
        var node: [String: Any] = ["role": Self.name(snapshot.elementType)]
        if !snapshot.label.isEmpty { node["label"] = snapshot.label }
        if !snapshot.identifier.isEmpty { node["id"] = snapshot.identifier }
        let value = Self.text(snapshot.value)
        if let value, !value.isEmpty { node["value"] = value }
        let states = Self.states(snapshot, value: value)
        if !states.isEmpty { node["states"] = states }
        let bare = node.count == 1
        if bare && Self.foldable.contains(snapshot.elementType) { return children }
        if snapshot.frame.isEmpty && children.isEmpty && snapshot.elementType != .application { return [] }
        if snapshot.elementType != .application {
            node["frame"] = [frame.minX, frame.minY, frame.width, frame.height].map { Int($0.rounded()) }
        }
        if !children.isEmpty { node["children"] = children }
        return [node]
    }

    private static func text(_ value: Any?) -> String? {
        switch value {
        case let string as String: string
        case let number as NSNumber: number.stringValue
        case nil: nil
        default: "\(value!)"
        }
    }

    private static let inputs: Set<XCUIElement.ElementType> = [.textField, .secureTextField, .searchField, .textView]

    private static func states(_ snapshot: XCUIElementSnapshot, value: String?) -> [String] {
        var states: [String] = []
        if snapshot.isSelected { states.append("selected") }
        if !snapshot.isEnabled { states.append("disabled") }
        // Web content reports focus on every element; only a field's focus says where typing goes.
        if snapshot.hasFocus && inputs.contains(snapshot.elementType) { states.append("focused") }
        if [.switch, .toggle, .checkBox].contains(snapshot.elementType) && value == "1" { states.append("checked") }
        return states
    }

    /// A PNG of the screen, scaled to points so that its pixels are the coordinates of `tap` and of the frames.
    private func screenshot() -> String {
        let image = XCUIScreen.main.screenshot().image
        let size = image.size
        let format = UIGraphicsImageRendererFormat()
        format.scale = 1
        let png = UIGraphicsImageRenderer(size: size, format: format).pngData { _ in
            image.draw(in: CGRect(origin: .zero, size: size))
        }
        return png.base64EncodedString()
    }

    /// The `XCUIElement.ElementType` names, by raw value.
    private static let names = [
        "Any", "Other", "Application", "Group", "Window", "Sheet", "Drawer", "Alert", "Dialog", "Button",
        "RadioButton", "RadioGroup", "CheckBox", "DisclosureTriangle", "PopUpButton", "ComboBox", "MenuButton",
        "ToolbarButton", "Popover", "Keyboard", "Key", "NavigationBar", "TabBar", "TabGroup", "Toolbar", "StatusBar",
        "Table", "TableRow", "TableColumn", "Outline", "OutlineRow", "Browser", "CollectionView", "Slider",
        "PageIndicator", "ProgressIndicator", "ActivityIndicator", "SegmentedControl", "Picker", "PickerWheel",
        "Switch", "Toggle", "Link", "Image", "Icon", "SearchField", "ScrollView", "ScrollBar", "StaticText",
        "TextField", "SecureTextField", "DatePicker", "TextView", "Menu", "MenuItem", "MenuBar", "MenuBarItem", "Map",
        "WebView", "IncrementArrow", "DecrementArrow", "Timeline", "RatingIndicator", "ValueIndicator", "SplitGroup",
        "Splitter", "RelevanceIndicator", "ColorWell", "HelpTag", "Matte", "DockItem", "Ruler", "RulerMarker", "Grid",
        "LevelIndicator", "Cell", "LayoutArea", "LayoutItem", "Handle", "Stepper", "Tab", "TouchBar", "StatusItem",
    ]

    static func name(_ type: XCUIElement.ElementType) -> String {
        let raw = Int(type.rawValue)
        return raw < names.count ? names[raw] : "Type\(raw)"
    }
}
