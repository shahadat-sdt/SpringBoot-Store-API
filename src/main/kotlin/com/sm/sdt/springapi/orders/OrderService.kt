package com.sm.sdt.springapi.orders

import com.sm.sdt.springapi.auth.AuthService
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val authService: AuthService,
    val orderMapper: OrderMapper
) {
    fun getAllOrders(): List<OrderDto> {
        val user = authService.getCurrentUser()
        val orders = orderRepository.getOrdersByCustomer(user)

        return orders.map { orderMapper.toOrderDto(it) }
    }

    fun getOrderById(id: Long): OrderDto {
        val user = authService.getCurrentUser()
        val order = orderRepository.getOrderWithItems(id)?: throw OrderNotFoundException()

        if(!order.isPlacedBy(user)){
            throw OrderAccessDeniedException()
        }

        return orderMapper.toOrderDto(order)



    }
}