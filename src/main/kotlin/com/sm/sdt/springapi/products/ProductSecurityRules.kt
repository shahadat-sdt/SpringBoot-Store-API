package com.sm.sdt.springapi.products

import com.sm.sdt.springapi.common.SecurityRules
import com.sm.sdt.springapi.users.Role
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class ProductSecurityRules: SecurityRules {
    override fun configure(request: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        request.requestMatchers(HttpMethod.GET, "/products").permitAll()
        request.requestMatchers(HttpMethod.POST, "/products").hasRole(Role.ADMIN.name)
        request.requestMatchers(HttpMethod.PUT, "/products").hasRole(Role.ADMIN.name)
        request.requestMatchers(HttpMethod.DELETE, "/products").hasRole(Role.ADMIN.name)
    }
}