//
//  ComponentHolder.swift
//  iosApp
//
//  Created by David Alejandro Coronel Baracaldo on 6/07/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ComponentHolder<T> {
    let lifecycle: LifecycleRegistry
    let component: T
    
    init(factory: (ComponentContext) -> T) {
        let lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        let context = DefaultComponentContext(lifecycle: lifecycle)
        let component = factory(context)
        self.lifecycle = lifecycle
        self.component = component
        
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
