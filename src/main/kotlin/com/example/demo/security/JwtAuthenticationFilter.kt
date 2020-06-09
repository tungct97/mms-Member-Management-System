package com.example.demo.security

import com.example.demo.service.CustomUserDetailsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter : OncePerRequestFilter() {
    @Autowired
    private val tokenProvider: JwtTokenProvider? = null

    @Autowired
    private val customUserDetailsService: CustomUserDetailsService? = null

    private val logger: Logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse?, filterChain: FilterChain) {
        try {
            val jwt = getJwtFromRequest(request)
            if (StringUtils.hasText(jwt) && tokenProvider!!.validateToken(jwt)) {
                val userId = tokenProvider.getUserIdFromJWT(jwt)
                val userDetails: UserDetails? = customUserDetailsService!!.loadUserById(userId!!)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails!!.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
//            logger.error("Could not set user authentication in security context", ex)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }
}