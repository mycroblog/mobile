import Accessibility
import Domain
import SwiftUI

/// The screen that asks for the address of a server and shows the instance it describes. It is shown in a sheet,
/// inside a navigation stack, and its Cancel dismisses the sheet.
public struct ServerLookupScreen: View {
    @Bindable private var model: ServerLookupModel
    @Environment(\.dismiss) private var dismiss
    /// Whether the address field has the keyboard; a lookup ends it, so that what it shows is not under the keyboard.
    @FocusState private var isAddressFocused: Bool

    private let choose: ((ServerAddress, Instance) -> Void)?

    /// The screen of `model`; with `choose`, a server that was looked up can be chosen to read.
    public init(model: ServerLookupModel, choose: ((ServerAddress, Instance) -> Void)? = nil) {
        self.model = model
        self.choose = choose
    }

    public var body: some View {
        Form {
            Section {
                HStack {
                    TextField("Server address", text: $model.address, prompt: Text(verbatim: "example.social"))
                        .keyboardType(.URL)
                        .textContentType(.URL)
                        .textInputAutocapitalization(.never)
                        .autocorrectionDisabled()
                        .accessibilityLabel(Text("Server address"))
                        .focused($isAddressFocused)
                        .submitLabel(.go)
                        .onSubmit(lookUp)
                    if !model.address.isEmpty {
                        Button {
                            model.address = ""
                            isAddressFocused = true
                        } label: {
                            Label("Clear", systemImage: "xmark.circle.fill")
                                .labelStyle(.iconOnly)
                                .foregroundStyle(.tertiary)
                        }
                        .buttonStyle(.borderless)
                    }
                }
                Button("Look up", action: lookUp)
                    .disabled(!model.canLookUp)
            } header: {
                Text("Server address")
            } footer: {
                if model.state == .invalidAddress {
                    Text(Self.invalidAddressMessage)
                        .foregroundStyle(.red)
                }
            }
            ServerLookupResult(state: model.state, tryAgain: tryAgain)
            if let choose, let server = model.loadedServer {
                Section {
                    Button("Read This Server") { choose(server.address, server.instance) }
                } footer: {
                    Text("Read the public timelines this server offers, without signing in.")
                }
            }
        }
        .navigationTitle("Choose a server")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .cancellationAction) {
                Button("Cancel", role: .cancel) { dismiss() }
            }
        }
        .task {
            // The field takes the keyboard when the screen opens with nothing looked up yet, once the sheet is up.
            guard model.state == .idle else { return }
            try? await Task.sleep(for: .milliseconds(350))
            isAddressFocused = true
        }
        .onChange(of: model.state) { _, state in
            if state == .invalidAddress {
                AccessibilityNotification.Announcement(String(localized: Self.invalidAddressMessage)).post()
            }
        }
    }

    private static let invalidAddressMessage: LocalizedStringResource =
        "Enter a server address such as example.social."

    private func lookUp() {
        isAddressFocused = false
        Task { await model.lookUp() }
    }

    private func tryAgain() {
        isAddressFocused = false
        Task { await model.tryAgain() }
    }
}

/// What a lookup shows below the address: its progress, the instance, or why it failed.
private struct ServerLookupResult: View {
    let state: ServerLookupModel.State
    let tryAgain: () -> Void

    var body: some View {
        switch state {
        case .idle, .invalidAddress:
            EmptyView()
        case .loading(let host):
            Section {
                HStack(spacing: 12) {
                    ProgressView()
                    Text("Looking up \(host)…")
                        .foregroundStyle(.secondary)
                }
                .accessibilityElement(children: .combine)
            }
        case .loaded(let host, let instance):
            Section {
                VStack(alignment: .leading, spacing: 4) {
                    Text(instance.name)
                        .font(.headline)
                        .accessibilityAddTraits(.isHeader)
                    if let shortDescription = instance.shortDescription {
                        Text(shortDescription)
                    }
                    Text(host)
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                }
                .padding(.vertical, 4)
            }
        case .failed(let host, let error):
            Section {
                VStack(alignment: .leading, spacing: 4) {
                    Text("Could not reach \(host).")
                        .font(.headline)
                    Text(error.reason)
                        .foregroundStyle(.secondary)
                }
                .padding(.vertical, 4)
                .accessibilityElement(children: .ignore)
                .accessibilityLabel(Text("Could not reach \(host). \(error.reason)"))
                Button("Try again", action: tryAgain)
            }
        }
    }
}

extension InstanceLookupError {
    /// Why the lookup failed, in plain words.
    fileprivate var reason: String {
        switch self {
        case .unreachable: String(localized: "The server did not answer.")
        case .notMycroblog: String(localized: "This is not a mycroblog server.")
        case .problem(let title, _): title
        }
    }
}
