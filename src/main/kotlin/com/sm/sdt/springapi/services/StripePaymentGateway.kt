package com.sm.sdt.springapi.services

import com.sm.sdt.springapi.entities.Order
import com.sm.sdt.springapi.entities.OrderItem
import com.sm.sdt.springapi.exceptions.PaymentException
import com.stripe.exception.StripeException
import com.stripe.model.checkout.Session
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class StripePaymentGateway(@Value("\${webAppUrl}") private val webAppUrl: String) : PaymentGateway {

    override fun createCheckoutSession(order: Order): CheckOutSession {
        try {
            val builder = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("$webAppUrl/checkout-success?orderId=${order.id}")
                .setCancelUrl("$webAppUrl/checkout-cancelled")

            order.items.forEach { item ->
                val lineItem = createLineItem(item)
                builder.addLineItem(lineItem)
            }

            val session = Session.create(builder.build())
            return CheckOutSession(session.url)
        } catch (ex: StripeException) {
            println(ex.message)
            throw PaymentException()
        }

    }

    private fun createLineItem(item: OrderItem): SessionCreateParams.LineItem? {
        return SessionCreateParams.LineItem.builder().setQuantity(item.quantity?.toLong())
            .setPriceData(createPriceData(item)).build()

    }

    private fun createPriceData(item: OrderItem): SessionCreateParams.LineItem.PriceData? =
        SessionCreateParams.LineItem.PriceData.builder()

            .setCurrency("BDT").setUnitAmountDecimal(item.unitPrice?.multiply(BigDecimal.valueOf(100)))
            .setProductData(createProductData(item)).build()

    private fun createProductData(item: OrderItem): SessionCreateParams.LineItem.PriceData.ProductData? =
        SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(item.product?.name).build()
}