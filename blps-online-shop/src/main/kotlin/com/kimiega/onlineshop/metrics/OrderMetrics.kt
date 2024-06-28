package com.kimiega.onlineshop.metrics

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class OrderMetrics(
    meterRegistry: MeterRegistry,
) {
    private val counter: Counter = meterRegistry.counter("order", "count", "1min")
    fun increaseOrderCounter() {
        counter.increment()
    }
}
