//
//  ObservablePagedValue.swift
//  iosApp
//
//  Created by David Alejandro Coronel Baracaldo on 12/07/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

public class ObservablePagedValue<T: Any, A:NSArray>: ObservableObject {
    private let pagedData:PagedData
    private var hasNextPage: Bool = false
    private var watcher : Cancellable? = nil
    
    @Published
    var values: [T] = []
    var loadingState : LoadingState = LoadingState.NotLoading
    
    init(_ pagedData: PagedData) {
        self.pagedData = pagedData
        loadingState = LoadingState.LoadingInitial
        watcher = pagedData.pagingData.watch { newValues in
            self.loadingState = LoadingState.NotLoading
            guard let list =  newValues?.compactMap({ $0 as? T }) else {
                return
            }            
            self.values = list
            self.hasNextPage = self.pagedData.hasNextPage
        }
    }
    
    func fetchNextData() {
        pagedData.loadNext()
        loadingState = LoadingState.LoadingMore
    }
    
    public var shouldDisplayNextPage: Bool {
        return hasNextPage
    }
    
    deinit {
        watcher?.cancel()
    }
}

public enum LoadingState {
case NotLoading
case LoadingInitial
case LoadingMore
}

