package com.sm.sdt.springapi.users

import com.sm.sdt.springapi.common.SecurityRules
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class UserSecurityRules : SecurityRules {
    override fun configure(request: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        request.requestMatchers(HttpMethod.POST, "/users").permitAll()
    }
}