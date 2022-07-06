import SwiftUI
import shared

struct ContentView: View {
    
    private var componentHolder = ComponentHolder { context in
        RootComponentImpl(componentContext: context)
    }
    
    var body: some View {
        VStack(alignment: .center) {
            TittleView(componentHolder.component)
            HStack(spacing: 8) {
                CounterView(componentHolder.component.counterChild)
                LettersView(componentHolder.component.lettersChild)
            }
        }
    }
}


struct TittleView: View {
    
    private let rootHost: RootComponent
    
    @ObservedObject
    var state: ObservableValue<RootState>
    
    init(_ rootHost: RootComponent) {
        self.rootHost = rootHost
        self.state = ObservableValue(FlowWrapper(origin: rootHost.state))
    }
    
    var body: some View {
        let char = String(Character(UnicodeScalar(self.state.value.character)!))
        Text("CounterComponent: \(self.state.value.counter) - Letter: \(char)")
    }
}


struct CounterView: View {
    
    private let counter: CounterComponent
    
    @ObservedObject
    var state: ObservableValue<CounterState>
    
    init(_ counter: CounterComponent) {
        self.counter = counter
        self.state = ObservableValue(FlowWrapper(origin: counter.state))
    }
    
    var body: some View {
        VStack(spacing: 8) {
            Text(String(self.state.value.count))
            Button(action: self.counter.incrementCounterBy10, label: { Text("Increment counter") }).padding(10)
        }.overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(.blue, lineWidth: 1)
        )
    }
}



struct LettersView: View {
    
    private let letters: AsciiComponent
    
    @ObservedObject
    var state: ObservableValue<AsciiState>
    
    init(_ letters: AsciiComponent) {
        self.letters = letters
        self.state = ObservableValue(FlowWrapper(origin: letters.state))
    }
    
    var body: some View {
        VStack(spacing: 8) {
            Text(String(Character(UnicodeScalar(self.state.value.character)!)))
            Button(action: self.letters.incrementAsciiValueBy10, label: { Text("Next letter") }).padding(10)
        }.overlay(
            RoundedRectangle(cornerRadius: 8)
                .stroke(.red, lineWidth: 1)
        )
    }
}
