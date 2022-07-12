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
    private let observableValue: FlowWrapper<A>
    private let pagingOptions:PagingOptions
    
    @Published
    var values: [T] = []
    var hasNextPage: Bool = false
    
    
    private var watcher : Cancellable? = nil
    
    init(_ value: FlowWrapper<A>,_ pagingOptions:PagingOptions) {
        self.observableValue = value
        self.pagingOptions = pagingOptions
        watcher = observableValue.watch { newValues in
            guard let list =  newValues?.compactMap({ $0 as? T }) else {
                return
            }
            
            self.values = list
            self.hasNextPage = self.pagingOptions.hasNextPage
        }
    }
    
    func fetchNextData() {
        pagingOptions.loadNext()
    }
    
    public var shouldDisplayNextPage: Bool {
        return hasNextPage
    }
    
    deinit {
        watcher?.cancel()
    }
}
