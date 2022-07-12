import SwiftUI
import shared

struct ContentView: View {
    
    private var componentHolder = ComponentHolder { context in
        RootComponentImpl(componentContext: context)
    }
    
    var body: some View {
        VStack(alignment: .center) {
            TittleView(componentHolder.component)
        }
    }
}


struct TittleView: View {
    
    private let rootHost: RootComponent
    
    @ObservedObject
    var state: ObservablePagedValue<Int32, NSArray>
    
    init(_ rootHost: RootComponent) {
        self.rootHost = rootHost
        self.state = ObservablePagedValue<Int32, NSArray>(FlowWrapper(origin: rootHost.state))
    }
    
    var body: some View {
        List {
            ForEach(state.values, id:\.self) { character in
                Text(String(character))
            }
            if state.shouldDisplayNextPage {
                nextPageView
            }
        }        
    }
    
    private var nextPageView: some View {
        HStack {
            Spacer()
            VStack {
                ProgressView()
                Text("Loading next page...")
            }
            Spacer()
        }
        .onAppear(perform: {
//            data.fetchNextData()
        })
    }
}

