package br.com.fugisawa.adopetbackendapi.config

import br.com.fugisawa.adopetbackendapi.security.JWTAuthenticationFilter
import br.com.fugisawa.adopetbackendapi.security.JWTAuthorizationFilter
import br.com.fugisawa.adopetbackendapi.security.TokenProvider
import br.com.fugisawa.adopetbackendapi.service.AuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebConfig(
    val securityProperties: SecurityProperties,
    val authenticationManager: AuthenticationManager,
    val tokenProvider: TokenProvider
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/actuator/health/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/actuator/info/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager, securityProperties, tokenProvider))
            .addFilter(JWTAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers",
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Authorization",
                "Content-Disposition",
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}
