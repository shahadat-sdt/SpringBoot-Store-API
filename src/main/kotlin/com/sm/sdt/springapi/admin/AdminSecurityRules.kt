package com.sm.sdt.springapi.admin

import com.sm.sdt.springapi.common.SecurityRules
import com.sm.sdt.springapi.users.Role
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.stereotype.Component

@Component
class AdminSecurityRules: SecurityRules {
    override fun configure(request: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry) {
        request.requestMatchers("/admin/**").hasRole(Role.ADMIN.name)
    }
}