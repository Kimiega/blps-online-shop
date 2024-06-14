package com.kimiega.onlineshop.dto.request

import com.kimiega.onlineshop.entity.PaymentResult
import com.kimiega.onlineshop.entity.PaymentStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

enum class StatusExternalPaymentRequest(s: String) {
    PAID("PAID"),
    NOT_PAID("NOT_PAID"),
    CANCELED("CANCELED");

    fun toPaymentStatus(): PaymentStatus = when (this) {
        PAID -> PaymentStatus.PAID
        NOT_PAID -> PaymentStatus.NOT_PAID
        CANCELED -> PaymentStatus.CANCELED
    }
}
data class ExternalPaymentResultRequest(
    @NotNull
    @Schema(example = "1")
    val paymentId: Long,
    @NotNull
    @Schema(example = "1")
    val orderId: Long,
    @NotNull
    @Schema(example = "PAID")
    val status: StatusExternalPaymentRequest,
) {
    fun toPaymentResult(): PaymentResult = PaymentResult(paymentId, orderId, status.toPaymentStatus())
}
