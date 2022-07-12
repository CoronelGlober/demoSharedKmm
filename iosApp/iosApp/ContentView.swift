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
    var pagedData: ObservablePagedValue<Int32, NSArray>
    
    init(_ rootHost: RootComponent) {
        self.rootHost = rootHost
        self.pagedData = ObservablePagedValue<Int32, NSArray>(rootHost.state)
    }
    
    var body: some View {
        if pagedData.loadingState == LoadingState.LoadingInitial {
            loadingInitial
        }else{
            List {
                ForEach(pagedData.values, id:\.self) { character in
                    Text(String(character))
                }
                if pagedData.shouldDisplayNextPage {
                    nextPageView
                }
            }     }
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
            pagedData.fetchNextData()
        })
    }
    
    private var loadingInitial: some View {
        HStack {
            Spacer()
            VStack {
                ProgressView()
                Text("Loading Initial page...")
            }
            Spacer()
        }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
    }
}

