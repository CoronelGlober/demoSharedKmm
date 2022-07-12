//
//  ObservableValue.swift
//  iosApp
//
//  Created by David Alejandro Coronel Baracaldo on 6/07/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

public class ObservableValue<T: AnyObject>: ObservableObject {
    private let observableValue: FlowWrapper<T>
    
    @Published
    var value: T? = nil
    
    
    private var watcher : Cancellable? = nil
    
    init(_ value: FlowWrapper<T>) {
        self.observableValue = value
        watcher = observableValue.watch { [weak self] (value) in
            self?.value = value!
        }
    }
    
    deinit {
        watcher?.cancel()
    }
}
