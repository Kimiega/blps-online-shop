package com.kimiega.onlineshop.dto.request

import com.kimiega.onlineshop.entity.DeliveryStatus
import com.kimiega.onlineshop.entity.EDeliveryStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

enum class StatusExternalDeliveryRequest(s: String) {
    DELIVERED("Delivered"),
    NOT_DELIVERED("Not delivered"),
    CANCELED("CANCELED");

    fun toDeliveryStatus(): EDeliveryStatus = when (this) {
        DELIVERED -> EDeliveryStatus.DELIVERED
        NOT_DELIVERED -> EDeliveryStatus.NOT_DELIVERED
        CANCELED -> EDeliveryStatus.CANCELED
    }
}
data class ExternalDeliveryResultRequest(
    @NotNull
    @Schema(example = "1")
    val orderId: Long,
    @NotNull
    @Schema(example = "DELIVERED")
    val status: StatusExternalDeliveryRequest,
) {
    fun toDeliveryStatus(): DeliveryStatus = DeliveryStatus(orderId, status.toDeliveryStatus())
}
