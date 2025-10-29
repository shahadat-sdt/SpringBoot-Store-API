package com.sm.sdt.springapi.payments

import com.sm.sdt.springapi.common.SecurityRules
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class PaymentSecurityRules : SecurityRules {
    override fun configure(request: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        request.requestMatchers(HttpMethod.POST, "/checkout/webhook").permitAll()
    }
}