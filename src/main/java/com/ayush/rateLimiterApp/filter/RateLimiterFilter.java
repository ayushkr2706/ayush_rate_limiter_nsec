package com.ayush.rateLimiterApp.filter;

import com.ayush.rateLimiterApp.config.RateLimiterConfig;
import com.ayush.rateLimiterApp.rateLimiter.TokenBucketRateLimiter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class RateLimiterFilter implements Filter {

    private TokenBucketRateLimiter tokenBucketRateLimiter;

    private RateLimiterConfig rateLimiterConfig;

    public RateLimiterFilter(TokenBucketRateLimiter tokenBucketRateLimiter, RateLimiterConfig rateLimiterConfig){
        this.tokenBucketRateLimiter = tokenBucketRateLimiter;
        this.rateLimiterConfig = rateLimiterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String identity = httpServletRequest.getRemoteAddr();       //IP address of the client
        int capacity = rateLimiterConfig.getCapacity();
        double refillRate = rateLimiterConfig.getRefillRate();

        boolean isAllowed = tokenBucketRateLimiter.isAllowed(identity, capacity, refillRate);

        if(isAllowed) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        else {

            httpServletResponse.setStatus(429);
            return;
        }

    }
}
