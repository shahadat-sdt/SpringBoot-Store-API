package com.sm.sdt.springapi.payments

import com.sm.sdt.springapi.orders.Order
import com.sm.sdt.springapi.orders.OrderItem
import com.stripe.exception.SignatureVerificationException
import com.stripe.exception.StripeException
import com.stripe.model.Event
import com.stripe.model.PaymentIntent
import com.stripe.model.checkout.Session
import com.stripe.net.Webhook
import com.stripe.param.checkout.SessionCreateParams
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
class StripePaymentGateway(
    @Value("\${webAppUrl}")
    private val webAppUrl: String,

    @Value("\${stripe.webhook_secret_key}")
    private val webhookSecretKey: String
) : PaymentGateway {

    override fun createCheckoutSession(order: Order): CheckOutSession {
        try {
            val builder = SessionCreateParams.builder().setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("$webAppUrl/checkout-success?orderId=${order.id}")
                .setCancelUrl("$webAppUrl/checkout-cancelled")
                .putMetadata("order_id", order.id.toString())

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

    override fun parseWebhookRequest(request: WebhookRequest): Optional<PaymentResults> {
        try {
            val payload = request.payload
            val signature = request.headers["stripe-signature"]
            val event = Webhook.constructEvent(payload, signature, webhookSecretKey)

            return when (event.type) {
                "payment_intent.succeeded" -> {
                    Optional.of(PaymentResults(extractOrderId(event), PaymentStatus.PAID))
                }

                "payment_intent.failed" -> {
                    Optional.of(PaymentResults(extractOrderId(event), PaymentStatus.FAILED))
                }

                else -> Optional.empty()
            }
        } catch (ex: SignatureVerificationException) {
            throw PaymentException("Invalid Signature")
        }

    }

    private fun extractOrderId(event: Event): Long {
        val stripeObj = event.dataObjectDeserializer.`object`.orElseThrow {
            PaymentException("Couldn't deserialize the stripe event. Please check the SDK and API version")
        }
        val paymentIntent = stripeObj as PaymentIntent
        return paymentIntent.metadata["order_id"]?.toLong()
            ?: throw PaymentException("Missing or invalid 'order_id' in Stripe metadata.")

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